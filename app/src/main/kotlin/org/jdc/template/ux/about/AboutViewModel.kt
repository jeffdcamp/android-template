package org.jdc.template.ux.about

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.jdc.template.domain.individual.usecase.CreateIndividualLargeTestDataUseCase
import org.jdc.template.domain.individual.usecase.CreateIndividualTestDataUseCase
import org.jdc.template.model.config.RemoteConfig
import org.jdc.template.model.datastore.DevicePreferenceDataSource
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.model.webservice.colors.ColorService
import org.jdc.template.model.webservice.colors.dto.ColorsDto
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import org.jdc.template.util.ext.readText
import org.jdc.template.util.network.ApiResponse
import org.jdc.template.util.network.message
import org.jdc.template.util.network.onError
import org.jdc.template.util.network.onException
import org.jdc.template.util.network.onFailure
import org.jdc.template.util.network.onSuccess
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.chats.ChatsRoute
import org.jdc.template.work.WorkScheduler
import javax.inject.Inject

@HiltViewModel
class AboutViewModel
@Inject constructor(
    private val application: Application,
    private val individualRepository: IndividualRepository,
    private val colorService: ColorService,
    private val workScheduler: WorkScheduler,
    private val remoteConfig: RemoteConfig,
    private val devicePreferenceDataSource: DevicePreferenceDataSource,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase,
    private val createIndividualLargeTestDataUseCase: CreateIndividualLargeTestDataUseCase,
    private val fileSystem: FileSystem
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {

    private val resetServiceEnabledFlow: StateFlow<Boolean> = MutableStateFlow(remoteConfig.isColorServiceEnabled()).asStateFlow()

    val uiState: AboutUiState = AboutUiState(
        resetServiceEnabledFlow = resetServiceEnabledFlow,
        testQueryWebServiceCall = { testQueryWebServiceCall() },
        testFullUrlQueryWebServiceCall = { testFullUrlQueryWebServiceCall() },
        testCachedUrlQueryWebServiceCall = { testCachedUrlQueryWebServiceCall() },
        testSaveQueryWebServiceCall = { testSaveQueryWebServiceCall() },
        workManagerSimpleTest = { workManagerSimpleTest() },
        workManagerSyncTest = { workManagerSyncTest() },
        testTableChange = { testTableChange() },
        licensesClick = { navigate(AcknowledgmentsRoute) },
        createSampleData = { createSampleData() },
        createLargeSampleData = { createLargeSampleData() },
        m3TypographyClick = { navigate(TypographyRoute) },
        onChatClick = { navigate(ChatsRoute) }
    )

    private fun testQueryWebServiceCall() = viewModelScope.launch {
        Logger.i { "TypeSafe Call..." }
        if (!remoteConfig.isColorServiceEnabled()) {
            Logger.e { "Color Service is NOT enabled... skipping" }
            return@launch
        }

        val response = colorService.getColorsBySafeArgs()
        processWebServiceResponse(response)

        Logger.i { "========================================================" }

        processWebServiceResponse2(colorService.getColorsBySafeArgs())
    }

    private fun testFullUrlQueryWebServiceCall() = viewModelScope.launch {
        Logger.i { "Full URL Call..." }
        val response = colorService.getColorsByFullUrl()
        processWebServiceResponse(response)
    }

    private fun testCachedUrlQueryWebServiceCall() = viewModelScope.launch {
        Logger.i { "Cached URL Call..." }

        val etag = devicePreferenceDataSource.colorsETagPref.flow.firstOrNull()

        colorService.getColorsCached(etag)
            .onSuccess {
                this.data?.colors?.forEach {
                    Logger.i { "Result: ${it.colorName}" }
                }

                this.etag?.let { devicePreferenceDataSource.colorsETagPref.setValue(it) }
            }
            .onException {
                Logger.e(this.throwable) { "Failed to fetch Colors" }
            }
    }

    private fun testSaveQueryWebServiceCall() = viewModelScope.launch {
        val outputFile = application.filesDir.toOkioPath() / "colors.json"
        colorService.getColorsToFile(fileSystem, outputFile)

        Logger.i { "Downloaded file contents:\n ${fileSystem.readText(outputFile)}" }
    }

    private fun processWebServiceResponse(response: ApiResponse<out ColorsDto, out Unit>) {
        response.onSuccess {
            data.colors.forEach {
                Logger.i { "Result: ${it.colorName}" }
            }
        }.onFailure {
            Logger.e { "Web Service FAILURE ${message()}" }
        }.onError {

        }.onException {

        }
    }

    private fun processWebServiceResponse2(response: ApiResponse<out ColorsDto, out Unit>) {
        response.onSuccess {
            data.colors.forEach {
                Logger.i { "Result: ${it.colorName}" }
            }
        }.onFailure {
            Logger.e { "Web Service FAILURE (message: [${message()}]" }
        }
    }

    private fun workManagerSimpleTest() = viewModelScope.launch {
        workScheduler.scheduleSimpleWork("test1")
        workScheduler.scheduleSimpleWork("test2")

        delay(3000)

        workScheduler.scheduleSimpleWork("test3")
    }

    private fun workManagerSyncTest() = viewModelScope.launch {
        workScheduler.scheduleSync()
        workScheduler.scheduleSync(true)

        delay(3000)

        workScheduler.scheduleSync()
    }

    private fun testTableChange() = viewModelScope.launch {
        // Sample tests
        if (individualRepository.getIndividualCount() == 0) {
            Logger.e { "No data.. cannot perform test" }
            return@launch
        }

        // Make some changes
        val originalName: FirstName?

        val individualList = individualRepository.getAllIndividuals()
        if (individualList.isNotEmpty()) {
            val individual = individualList[0]
            originalName = individual.firstName
            Logger.i { "ORIGINAL NAME = $originalName" }

            // change name
            individualRepository.saveIndividual(individual.copy(firstName = FirstName("Bobby")))

            // restore name
            individualRepository.saveIndividual(individual.copy(firstName = originalName))
        } else {
            Logger.e { "Cannot find individual" }
        }
    }

    private fun createSampleData() = viewModelScope.launch {
        createIndividualTestDataUseCase()
    }

    private fun createLargeSampleData() = viewModelScope.launch {
        createIndividualLargeTestDataUseCase()
    }
}
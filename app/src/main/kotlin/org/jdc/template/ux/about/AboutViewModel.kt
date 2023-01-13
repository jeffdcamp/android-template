package org.jdc.template.ux.about

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.domain.individual.CreateIndividualLargeTestDataUseCase
import org.jdc.template.domain.individual.CreateIndividualTestDataUseCase
import org.jdc.template.model.config.RemoteConfig
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.model.webservice.colors.ColorService
import org.jdc.template.model.webservice.colors.dto.ColorsDto
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.ext.saveBodyToFile
import org.jdc.template.ux.about.samples.ComponentsRoute
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.work.WorkScheduler
import retrofit2.Response
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class  AboutViewModel
@Inject constructor(
    private val application: Application,
    private val individualRepository: IndividualRepository,
    private val colorService: ColorService,
    private val workScheduler: WorkScheduler,
    private val remoteConfig: RemoteConfig,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase,
    private val createIndividualLargeTestDataUseCase: CreateIndividualLargeTestDataUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val resetServiceEnabledFlow: StateFlow<Boolean> = MutableStateFlow(remoteConfig.isColorServiceEnabled()).asStateFlow()

    val uiState: AboutUiState = AboutUiState(
        resetServiceEnabledFlow = resetServiceEnabledFlow,
        testQueryWebServiceCall = { testQueryWebServiceCall() },
        testFullUrlQueryWebServiceCall = { testFullUrlQueryWebServiceCall() },
        testSaveQueryWebServiceCall = { testSaveQueryWebServiceCall() },
        workManagerSimpleTest = { workManagerSimpleTest() },
        workManagerSyncTest = { workManagerSyncTest() },
        testTableChange = { testTableChange() },
        licensesClicked = { navigate(AcknowledgmentsRoute.createRoute()) },
        createSampleData = { createSampleData() },
        createLargeSampleData = { createLargeSampleData() },
        m3SamplesClicked = { navigate(ComponentsRoute.createRoute()) },
        m3TypographyClicked = { navigate(TypographyRoute.createRoute()) }
    )

    private fun testQueryWebServiceCall() = viewModelScope.launch {
        if (!remoteConfig.isColorServiceEnabled()) {
            Timber.e("Color Service is NOT enabled... skipping")
            return@launch
        }

        val response = colorService.colors()

        if (response.isSuccessful) {
            processWebServiceResponse(response)
        } else {
            Timber.e("Failed to get colors from webservice [${response.errorBody()}]")
        }
    }

    private fun testFullUrlQueryWebServiceCall() = viewModelScope.launch {
        val response = colorService.colorsByFullUrl(ColorService.FULL_URL)

        if (response.isSuccessful) {
            processWebServiceResponse(response)
        } else {
            Timber.e("Search FAILED [${response.errorBody()}]")
        }
    }

    private fun testSaveQueryWebServiceCall() = viewModelScope.launch {
        val response = colorService.colorsToFile()

        if (response.isSuccessful) {
            // delete any existing file
            val outputFile = File(application.externalCacheDir, "ws-out.json")
            if (outputFile.exists()) {
                outputFile.delete()
            }

            // save the response body to file
            response.saveBodyToFile(outputFile)

            // show the output of the file
            val fileContents = outputFile.readText()
            Timber.i("Output file: [$fileContents]")
        } else {
            Timber.e("Search FAILED [${response.errorBody()}]")
        }
    }

    private fun processWebServiceResponse(response: Response<ColorsDto>) {
        if (response.isSuccessful) {
            Timber.i("Search SUCCESS")
            response.body()?.let {
                processSearchResponse(it)
            }
        } else {
            Timber.e("Search FAILURE: code (%d)", response.code())
        }
    }

    private fun processSearchResponse(colorsDto: ColorsDto) {
        for (dtoResult in colorsDto.colors) {
            Timber.i("Result: %s", dtoResult.colorName)
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
            Timber.e("No data.. cannot perform test")
            return@launch
        }

        // Make some changes
        val originalName: String?

        val individualList = individualRepository.getAllIndividuals()
        if (individualList.isNotEmpty()) {
            val individual = individualList[0]
            originalName = individual.firstName
            Timber.i("ORIGINAL NAME = %s", originalName)

            // change name
            individual.firstName = "Bobby"
            individualRepository.saveIndividual(individual)

            // restore name
            individual.firstName = originalName
            individualRepository.saveIndividual(individual)
        } else {
            Timber.e("Cannot find individual")
        }
    }

    private fun createSampleData() = viewModelScope.launch {
        createIndividualTestDataUseCase()
    }

    private fun createLargeSampleData() = viewModelScope.launch {
        createIndividualLargeTestDataUseCase()
    }
}
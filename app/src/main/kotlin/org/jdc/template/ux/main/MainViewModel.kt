package org.jdc.template.ux.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.jdc.template.domain.individual.usecase.CreateIndividualTestDataUseCase
import org.jdc.template.model.domain.type.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository
import org.jdc.template.ui.navigation.DefaultNavigationBarConfig
import org.jdc.template.ui.navigation.ViewModelNavigationBar
import org.jdc.template.ui.navigation.ViewModelNavigationBarImpl
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.work.WorkScheduler
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val workScheduler: WorkScheduler,
    settingsRepository: SettingsRepository,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase
) : ViewModel(), ViewModelNavigationBar<NavBarItem> by ViewModelNavigationBarImpl(NavBarItem.PEOPLE, DefaultNavigationBarConfig(NavBarItem.getNavBarItemRouteMap())) {
    val uiState = MainUiState(
        selectedAppThemeFlow = combine(
            settingsRepository.themeFlow.stateInDefault(viewModelScope, null),
            settingsRepository.dynamicThemeFlow.stateInDefault(viewModelScope, null)) { displayThemeType, dynamicTheme ->
            SelectedAppTheme(displayThemeType ?: DisplayThemeType.SYSTEM_DEFAULT, dynamicTheme ?: false)
        }.stateInDefault(viewModelScope, null)
    )

    private var startupComplete = false
    fun startup() = viewModelScope.launch {
        if (startupComplete) {
            return@launch
        }

        // run any startup/initialization code here (NOTE: these tasks should NOT exceed 1000ms (per Google Guidelines))
        Logger.i { "Startup task..." }

        // schedule workers
        workScheduler.startPeriodicWorkSchedules()

        // Startup finished
        Logger.i { "Startup finished" }

        startupComplete = true
    }

    @VisibleForTesting
    suspend fun createSampleData() {
        createIndividualTestDataUseCase()
    }
}

data class SelectedAppTheme(val displayThemeType: DisplayThemeType, val dynamicTheme: Boolean)
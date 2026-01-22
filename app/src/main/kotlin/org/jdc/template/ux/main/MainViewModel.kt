package org.jdc.template.ux.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.jdc.template.shared.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.shared.model.repository.SettingsRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.work.WorkScheduler

class MainViewModel(
    private val workScheduler: WorkScheduler,
    settingsRepository: SettingsRepository,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase
) : ViewModel() {
    val uiStateFlow: StateFlow<MainUiState> = combine(
        settingsRepository.themeFlow,
        settingsRepository.dynamicThemeFlow
    ) { displayThemeType, dynamicTheme ->
        MainUiState.Ready(SelectedAppTheme(displayThemeType, dynamicTheme))
    }.stateInDefault(viewModelScope, MainUiState.Loading)

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

sealed interface MainUiState {
    data object Loading : MainUiState

    data class Ready(
        val selectedAppTheme: SelectedAppTheme,
    ) : MainUiState
}

data class SelectedAppTheme(val displayThemeType: DisplayThemeType, val dynamicTheme: Boolean)

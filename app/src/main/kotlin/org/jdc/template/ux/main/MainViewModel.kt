package org.jdc.template.ux.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.model.repository.SettingsRepository
import org.jdc.template.util.ext.stateInDefault
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    settingsRepository: SettingsRepository,
    private val individualRepository: IndividualRepository
) : ViewModel() {
    val themeFlow: StateFlow<DisplayThemeType?> = settingsRepository.themeFlow.stateInDefault(viewModelScope, null)

    var isReady: Boolean = false
        private set

    fun startup() = viewModelScope.launch {
        // run any startup/initialization code here (NOTE: these tasks should NOT exceed 1000ms (per Google Guidelines))
        Timber.i("Startup task...")

        // Startup finished
        isReady = true
        Timber.i("Startup finished")
    }

    @VisibleForTesting
    suspend fun createSampleData() {
        individualRepository.createSampleData()
    }
}
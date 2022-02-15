package org.jdc.template.ux.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    var isReady: Boolean = false
        private set

    fun startup() = viewModelScope.launch {
        // run any startup/initialization code here (NOTE: these tasks should NOT exceed 1000ms (per Google Guidelines))
        Timber.i("Startup task...")

        // Startup finished
        isReady = true
        Timber.i("Startup finished")
    }

    val themeFlow: Flow<DisplayThemeType> get() = settingsRepository.themeFlow
}
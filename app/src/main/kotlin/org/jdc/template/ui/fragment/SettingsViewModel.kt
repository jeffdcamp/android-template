package org.jdc.template.ui.fragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jdc.template.model.prefs.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository

class SettingsViewModel
@ViewModelInject constructor(
    private val settingsRepository: SettingsRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun setTheme(theme: DisplayThemeType) = viewModelScope.launch {
        settingsRepository.setTheme(theme)
    }
}
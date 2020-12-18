package org.jdc.template.ux.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.jdc.template.model.prefs.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository

class MainViewModel
@ViewModelInject constructor(
    private val settingsRepository: SettingsRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    val themeFlow: Flow<DisplayThemeType> get() = settingsRepository.themeFlow
}
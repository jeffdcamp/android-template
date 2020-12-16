package org.jdc.template.ui.fragment

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.prefs.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository

class SettingsViewModel
@ViewModelInject constructor(
    private val application: Application,
    private val settingsRepository: SettingsRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    val currentThemeTitleFlow: Flow<String> get() = settingsRepository.themeFlow.map { theme ->
        when (theme) {
            DisplayThemeType.SYSTEM_DEFAULT -> application.getText(R.string.system_default).toString()
            DisplayThemeType.LIGHT -> application.getText(R.string.light).toString()
            DisplayThemeType.DARK -> application.getText(R.string.dark).toString()
        }
    }
    fun setTheme(theme: DisplayThemeType) = viewModelScope.launch {
        settingsRepository.setTheme(theme)
    }
}
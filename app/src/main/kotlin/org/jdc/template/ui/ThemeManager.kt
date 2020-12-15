package org.jdc.template.ui

import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.flow.first
import org.jdc.template.model.prefs.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager
@Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun applyTheme(theme: DisplayThemeType? = null) {
        when (theme ?: settingsRepository.themeFlow.first()) {
            DisplayThemeType.SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            DisplayThemeType.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DisplayThemeType.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
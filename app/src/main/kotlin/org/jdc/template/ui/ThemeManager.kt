package org.jdc.template.ui

import androidx.appcompat.app.AppCompatDelegate
import org.jdc.template.prefs.DisplayThemeType
import org.jdc.template.prefs.Prefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager
@Inject constructor(
    private val prefs: Prefs
){
    fun saveAndApplyTheme(theme: DisplayThemeType) {
        prefs.theme = theme
        applyTheme()
    }

    fun applyTheme(theme: DisplayThemeType = prefs.theme) {
        when (theme) {
            DisplayThemeType.SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            DisplayThemeType.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DisplayThemeType.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
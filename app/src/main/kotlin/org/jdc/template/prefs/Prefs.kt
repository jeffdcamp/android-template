package org.jdc.template.prefs

import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor() : PrefsContainer(COMMON_NAMESPACE)  {
    var developerMode by SharedPref(false, key = PREF_DEVELOPER_MODE)
    var theme by EnumPref(getThemeDefault(), key = PREF_GENERAL_DISPLAY_THEME_TYPE)

    private fun getThemeDefault(): DisplayThemeType {
        return if (Build.VERSION.SDK_INT > 28) {
            // support Android Q System Theme
            DisplayThemeType.SYSTEM_DEFAULT
        } else {
            DisplayThemeType.LIGHT
        }
    }

    companion object {
        private const val PREF_DEVELOPER_MODE = "developer_mode"
        const val PREF_GENERAL_DISPLAY_THEME_TYPE = "display_theme_type"
    }
}


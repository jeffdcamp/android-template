package org.jdc.template.prefs

import android.os.Build
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor() : PrefsContainer(COMMON_NAMESPACE)  {
    var developerMode by SharedPref(false, key = PREF_DEVELOPER_MODE)
    var theme by EnumPref(getThemeDefault(), key = PREF_GENERAL_DISPLAY_THEME_TYPE)
    private var internalAppInstanceId by SharedPref("", key = PREF_APP_INSTANCE_ID)

    private fun getThemeDefault(): DisplayThemeType {
        return if (Build.VERSION.SDK_INT > 28) {
            // support Android Q System Theme
            DisplayThemeType.SYSTEM_DEFAULT
        } else {
            DisplayThemeType.LIGHT
        }
    }

    @Synchronized
    fun getAppInstanceId(): String {
        if (internalAppInstanceId.isBlank()) {
            internalAppInstanceId = UUID.randomUUID().toString()
        }

        return internalAppInstanceId
    }

    companion object {
        private const val PREF_DEVELOPER_MODE = "developer_mode"
        private const val PREF_APP_INSTANCE_ID = "app_instance_id"
        const val PREF_GENERAL_DISPLAY_THEME_TYPE = "display_theme_type"
    }
}


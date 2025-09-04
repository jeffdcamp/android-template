package org.jdc.template.ux.settings

import androidx.navigation3.runtime.NavKey

object SettingsRoute: NavKey

// todo: handle deep links
//fun SettingsRoute.deeplinks() = listOf(
//    // Simple deep link without any path/query arguments
//    // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://settings"
//    navDeepLink<SettingsRoute>("${NavIntentFilterPart.DEFAULT_APP_SCHEME}://settings"),
//)

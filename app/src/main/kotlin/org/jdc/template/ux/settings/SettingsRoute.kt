package org.jdc.template.ux.settings

import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import org.jdc.template.ui.navigation.NavigationRoute
import org.jdc.template.ux.NavIntentFilterPart

@Serializable
object SettingsRoute: NavigationRoute

fun SettingsRoute.deeplinks() = listOf(
    // Simple deep link without any path/query arguments
    // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://settings"
    navDeepLink<SettingsRoute>("${NavIntentFilterPart.DEFAULT_APP_SCHEME}://settings"),
)

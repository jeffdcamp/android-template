package org.jdc.template.ux.settings

import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import org.jdc.template.ui.navigation.NavigationRoute

@Serializable
object SettingsRoute: NavigationRoute

fun SettingsRoute.deeplinks() = listOf(
    // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://settings"
    navDeepLink<SettingsRoute>("${org.jdc.template.ux.NavIntentFilterPart.DEFAULT_APP_SCHEME}://settings"),
)

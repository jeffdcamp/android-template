package org.jdc.template.ux.settings

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jdc.template.shared.util.network.toUri
import org.jdc.template.ui.navigation.deeplink.SimpleRouteMatcher

@Serializable
object SettingsRoute: NavKey

//./adb shell am start -W -a android.intent.action.VIEW -d "android-template://settings"
object SettingsRouteMatcher : SimpleRouteMatcher<SettingsRoute>(SettingsRoute, "/settings".toUri())

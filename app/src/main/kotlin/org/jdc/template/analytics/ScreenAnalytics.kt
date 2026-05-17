package org.jdc.template.analytics

import androidx.navigation3.runtime.NavKey
import co.touchlab.kermit.Logger
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.chats.ChatsRoute
import org.jdc.template.ux.directory.DirectoryRoute
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.settings.SettingsRoute

class ScreenAnalytics(
    private val analytics: Analytics,
) {
    fun logScreenViewed(route: NavKey) {
        val analyticsScreenName = when (route) {
            is DirectoryRoute -> Analytics.Screen.DIRECTORY
            is IndividualRoute -> Analytics.Screen.INDIVIDUAL
            is IndividualEditRoute -> Analytics.Screen.INDIVIDUAL_EDIT
            is ChatsRoute -> Analytics.Screen.CHATS
            is ChatRoute -> Analytics.Screen.CHAT
            is SettingsRoute -> Analytics.Screen.SETTINGS
            is AboutRoute -> Analytics.Screen.ABOUT
            is TypographyRoute -> Analytics.Screen.TYPOGRAPHY
            is AcknowledgmentsRoute -> Analytics.Screen.ACKNOWLEDGMENTS
            else -> null
        }

        if (analyticsScreenName == null) {
            Logger.w { "Missing analyticsScreenName for route [${route::class.simpleName}]" }
            return
        }

        analytics.logScreen(AnalyticScreen(analyticsScreenName, AppAnalytics.ScopeLevel.DEV))
    }
}

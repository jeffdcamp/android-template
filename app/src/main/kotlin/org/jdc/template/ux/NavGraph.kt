package org.jdc.template.ux

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.jdc.template.BuildConfig
import org.jdc.template.ui.navigation.NavUriLogger
import org.jdc.template.ui.navigation.WorkManagerStatusRoute
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.about.AboutScreen
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.about.typography.TypographyScreen
import org.jdc.template.ux.acknowledgement.AcknowledgementScreen
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.chat.ChatScreen
import org.jdc.template.ux.chat.typeMap
import org.jdc.template.ux.chats.ChatsRoute
import org.jdc.template.ux.chats.ChatsScreen
import org.jdc.template.ux.directory.DirectoryRoute
import org.jdc.template.ux.directory.DirectoryScreen
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individual.IndividualScreen
import org.jdc.template.ux.individual.deepLinks
import org.jdc.template.ux.individual.typeMap
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.individualedit.IndividualEditScreen
import org.jdc.template.ux.individualedit.typeMap
import org.jdc.template.ux.settings.SettingsRoute
import org.jdc.template.ux.settings.SettingsScreen
import org.jdc.template.ux.settings.deeplinks

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    // Debug navigation
    if (BuildConfig.DEBUG) {
        navController.addOnDestinationChangedListener(NavUriLogger())
    }

    NavHost(
        navController = navController,
        startDestination = DirectoryRoute
    ) {
        composable<DirectoryRoute> { DirectoryScreen(navController) }
        composable<IndividualRoute>(IndividualRoute.typeMap(), IndividualRoute.deepLinks()) { IndividualScreen(navController) }
        composable<IndividualEditRoute>(IndividualEditRoute.typeMap()) { IndividualEditScreen(navController) }
        composable<ChatsRoute> { ChatsScreen(navController) }
        composable<ChatRoute>(ChatRoute.typeMap()) { ChatScreen(navController) }
        composable<SettingsRoute>(deepLinks = SettingsRoute.deeplinks()) { SettingsScreen(navController) }
        composable<AboutRoute> { AboutScreen(navController) }
        composable<TypographyRoute> { TypographyScreen(navController) }
        composable<AcknowledgmentsRoute> { AcknowledgementScreen(navController) }
        composable<WorkManagerStatusRoute> { navController.popBackStack() }
    }
}

/**
 * Used for building Compose Navigation deeplinks.
 *
 * Provides common values to build navDeepLink uriPattern (used in AndroidManifest intent-filter)
 */
object NavIntentFilterPart {
    const val DEFAULT_APP_SCHEME = "android-template"
    const val DEFAULT_WEB_SCHEME_HOST = "http://www.mysite.com"
}

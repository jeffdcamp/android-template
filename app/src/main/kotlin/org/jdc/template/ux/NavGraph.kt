package org.jdc.template.ux

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen
import org.jdc.template.ui.navigation.NavUriLogger
import org.jdc.template.ui.navigation.WorkManagerStatusRoute
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.about.AboutScreen
import org.jdc.template.ux.about.typography.TypographyScreen
import org.jdc.template.ux.about.samples.ComponentDetailsRoute
import org.jdc.template.ux.about.samples.ComponentDetailsScreen
import org.jdc.template.ux.about.samples.ComponentExampleRoute
import org.jdc.template.ux.about.samples.ComponentExampleScreen
import org.jdc.template.ux.about.samples.ComponentsRoute
import org.jdc.template.ux.about.samples.ComponentsScreen
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.acknowledgement.AcknowledgementScreen
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.directory.DirectoryRoute
import org.jdc.template.ux.directory.DirectoryScreen
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individual.IndividualScreen
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.individualedit.IndividualEditScreen
import org.jdc.template.ux.settings.SettingsRoute
import org.jdc.template.ux.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Debug navigation
    navController.addOnDestinationChangedListener(NavUriLogger())

    NavHost(
        navController = navController,
        startDestination = DirectoryRoute.routeDefinition
    ) {
        DirectoryRoute.addNavigationRoute(this) { DirectoryScreen(navController) }
        IndividualRoute.addNavigationRoute(this) { IndividualScreen(navController) }
        IndividualEditRoute.addNavigationRoute(this) { IndividualEditScreen(navController) }

        SettingsRoute.addNavigationRoute(this) { SettingsScreen(navController) }
        AboutRoute.addNavigationRoute(this) { AboutScreen(navController) }

        ComponentsRoute.addNavigationRoute(this) { ComponentsScreen(navController) }
        ComponentDetailsRoute.addNavigationRoute(this) { ComponentDetailsScreen(navController) }
        ComponentExampleRoute.addNavigationRoute(this) { ComponentExampleScreen(navController) }
        TypographyRoute.addNavigationRoute(this) { TypographyScreen(navController) }

        AcknowledgmentsRoute.addNavigationRoute(this) { AcknowledgementScreen(navController) }

        WorkManagerStatusRoute.addNavigationRoute(this) { WorkManagerStatusScreen { navController.popBackStack() } }
    }
}

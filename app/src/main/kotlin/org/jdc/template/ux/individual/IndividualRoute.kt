package org.jdc.template.ux.individual

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.jdc.template.ui.navigation.NavComposeRoute
import org.jdc.template.ui.navigation.RouteUtil

object IndividualRoute : NavComposeRoute() {
    override val routeDefinition: String = "individual/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}" // individual/individualId={individualId}

    fun createRoute(individualId: String): String {
        return "individual/$individualId" // individual/123456
    }

    override fun <D : NavDestination> NavDestinationBuilder<D>.setupNav() {
        argument(Arg.INDIVIDUAL_ID) {
            type = NavType.StringType
        }

        // Test Deeplink (change the id as needed)
        // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://individual/dee40424-f42d-48ab-a13d-bae76b3bb3cd"
        deepLink {
            uriPattern = "android-template://$routeDefinition"
        }
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}
package org.jdc.template.ux.individual

import android.content.Context
import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavType
import org.jdc.template.R
import org.jdc.template.ui.navigation.NavFragmentRoute
import org.jdc.template.ui.navigation.RouteUtil

object IndividualRoute : NavFragmentRoute() {
    override fun getLabel(context: Context): String {
        return context.getString(R.string.individual)
    }

    override val routeDefinition: String = "individual/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}" // individual/individualId={individualId}

    fun createRoute(individualId: String): String {
        return "individual/$individualId"  // individual/123456
    }

    override fun <D : NavDestination> NavDestinationBuilder<D>.setupNav() {
        argument(Arg.INDIVIDUAL_ID) {
            type = NavType.StringType
        }

        // Test Deeplink (change the id as needed)
        // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://individual/dee40424-f42d-48ab-a13d-bae76b3bb3cd"
        deepLink {
            uriPattern = "android-template://${routeDefinition}"
        }
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}
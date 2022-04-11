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

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.INDIVIDUAL_ID) {
                type = NavType.StringType
            }
        )
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}
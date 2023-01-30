package org.jdc.template.ux.individualedit

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.jdc.template.ui.navigation.NavComposeRoute
import org.jdc.template.ui.navigation.RouteUtil

object IndividualEditRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "individualEdit"
    override val routeDefinition: String = "$ROUTE_BASE?${RouteUtil.defineOptionalArgs(Arg.INDIVIDUAL_ID)}"

    fun createRoute(individualId: String? = null): String {
        return "$ROUTE_BASE?${RouteUtil.optionalArgs(mapOf(Arg.INDIVIDUAL_ID to individualId))}"
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.INDIVIDUAL_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}
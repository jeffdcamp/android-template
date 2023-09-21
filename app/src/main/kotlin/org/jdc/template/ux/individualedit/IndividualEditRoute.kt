package org.jdc.template.ux.individualedit

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavComposeRoute
import org.jdc.template.ui.navigation.NavRoute
import org.jdc.template.ui.navigation.NavRouteDefinition
import org.jdc.template.ui.navigation.RouteUtil
import org.jdc.template.ui.navigation.asNavRoute
import org.jdc.template.ui.navigation.asNavRouteDefinition

object IndividualEditRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "individualEdit"
    override val routeDefinition: NavRouteDefinition = "$ROUTE_BASE?${RouteUtil.defineOptionalArgs(Arg.INDIVIDUAL_ID)}".asNavRouteDefinition()

    fun createRoute(individualId: IndividualId? = null): NavRoute {
        return "$ROUTE_BASE?${RouteUtil.optionalArgs(mapOf(Arg.INDIVIDUAL_ID to individualId?.value))}".asNavRoute()
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
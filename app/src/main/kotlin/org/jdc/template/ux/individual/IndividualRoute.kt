package org.jdc.template.ux.individual

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavComposeRoute
import org.jdc.template.ui.navigation.RouteUtil

object IndividualRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "individual"
    override val routeDefinition: String = "$ROUTE_BASE/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}" // individual/{individualId}

    fun createRoute(individualId: IndividualId): String {
        return "$ROUTE_BASE/${individualId.value}" // individual/123456
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
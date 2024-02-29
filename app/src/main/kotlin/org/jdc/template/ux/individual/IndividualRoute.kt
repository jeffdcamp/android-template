package org.jdc.template.ux.individual

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavComposeRoute
import org.jdc.template.ui.navigation.NavRoute
import org.jdc.template.ui.navigation.NavRouteDefinition
import org.jdc.template.ui.navigation.RouteUtil
import org.jdc.template.ui.navigation.asNavRoute
import org.jdc.template.ui.navigation.asNavRouteDefinition
import org.jdc.template.util.ext.requireIndividualId

object IndividualRoute : NavComposeRoute() {
    private const val ROUTE_BASE = "individual"
    override val routeDefinition: NavRouteDefinition = "$ROUTE_BASE/${RouteUtil.defineArg(Arg.INDIVIDUAL_ID)}".asNavRouteDefinition() // individual/{individualId}

    fun createRoute(individualId: IndividualId): NavRoute {
        return "$ROUTE_BASE/${individualId.value}".asNavRoute() // individual/123456
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.INDIVIDUAL_ID) {
                type = NavType.StringType
            }
        )
    }

    // adb shell am start -W -a android.intent.action.VIEW -d "android-template://individual/xxxxxx"
    override fun getDeepLinks(): List<NavDeepLink> {
        return listOf(
            navDeepLink { uriPattern = "android-template://${ROUTE_BASE}/{${Arg.INDIVIDUAL_ID}}" }
        )
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}

data class IndividualArgs(val individualId: IndividualId) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                savedStateHandle.requireIndividualId(IndividualRoute.Arg.INDIVIDUAL_ID),
            )
}

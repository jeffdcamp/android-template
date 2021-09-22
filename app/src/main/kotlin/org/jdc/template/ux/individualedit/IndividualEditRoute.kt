package org.jdc.template.ux.individualedit

import android.content.Context
import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavType
import org.jdc.template.R
import org.jdc.template.ui.navigation.NavFragmentRoute
import org.jdc.template.ui.navigation.RouteUtil

object IndividualEditRoute: NavFragmentRoute() {
    override fun getLabel(context: Context): String {
        return context.getString(R.string.edit_individual)
    }

    override fun defineRoute(): String {
        return "individualEdit?${RouteUtil.defineOptionalArgs(Arg.INDIVIDUAL_ID)}"
    }

    fun createRoute(individualId: String? = null): String {
        return "individualEdit?${RouteUtil.optionalArgs(mapOf(Arg.INDIVIDUAL_ID to individualId))}"
    }

    override fun <D : NavDestination> NavDestinationBuilder<D>.setupNav() {
        argument(Arg.INDIVIDUAL_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
    }

    object Arg {
        const val INDIVIDUAL_ID = "individualId"
    }
}
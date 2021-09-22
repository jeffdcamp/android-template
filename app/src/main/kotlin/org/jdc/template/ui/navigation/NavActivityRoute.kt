@file:Suppress("unused")

package org.jdc.template.ui.navigation

import android.app.Activity
import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavGraphBuilder
import androidx.navigation.activity

abstract class NavActivityRoute {
    /**
     * Route definition
     */
    abstract fun defineRoute(): String

    /**
     * Define arguments, routes, deeplinks, etc for the default activity
     */
    abstract fun <D: NavDestination> NavDestinationBuilder<D>.setupNav()

    /**
     * Used when creating navigation graph
     */
    inline fun <reified T: Activity> addNavigationRoute(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            // default fragment
            activity(defineRoute()) {
                activityClass = T::class
                setupNav()
            }
        }
    }
}

/**
 * Simple Route that requires no arguments
 */
abstract class SimpleNavActivityRoute(val route: String, val labelId: Int? = null) : NavActivityRoute() {
    override fun defineRoute(): String = route

    override fun <D : NavDestination> NavDestinationBuilder<D>.setupNav() {
    }
}

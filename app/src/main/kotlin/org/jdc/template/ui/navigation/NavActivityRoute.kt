@file:Suppress("unused")

package org.jdc.template.ui.navigation

import android.app.Activity
import android.content.Context
import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavGraphBuilder
import androidx.navigation.activity

abstract class NavActivityRoute {
    /**
     * Route definition
     */
    abstract val routeDefinition: String

    /**
     * Route used when navigating
     *
     * Each class that implements NavActivityRoute should implement this method with the needed args to fulfil the routeDefinition
     */
    // fun createRoute(): String = routeDefinition

    /**
     * Define arguments, routes, deeplinks, etc for the default activity
     */
    abstract fun <D : NavDestination> NavDestinationBuilder<D>.setupNav()

    /**
     * Used when creating navigation graph
     */
    inline fun <reified T : Activity> addNavigationRoute(navGraphBuilder: NavGraphBuilder, context: Context) {
        navGraphBuilder.apply {
            // default fragment
            activity(this@NavActivityRoute.routeDefinition) {
                label = getLabel(context)
                activityClass = T::class
                setupNav()
            }
        }
    }

    /**
     * Define label for route
     */
    open fun getLabel(context: Context): String? = null
}

/**
 * Simple Route that requires no arguments
 */
abstract class SimpleNavActivityRoute(override val routeDefinition: String, val labelId: Int? = null) : NavActivityRoute() {
    /**
     * Route used when navigating
     */
    fun createRoute(): String = routeDefinition

    override fun <D : NavDestination> NavDestinationBuilder<D>.setupNav() {
    }

    override fun getLabel(context: Context): String? {
        return labelId?.let { context.getString(it) }
    }
}

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package org.jdc.template.ui.navigation

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog

abstract class NavDialogRoute {
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
     * Define arguments, routes, deeplinks, etc for the default fragment
     */
    abstract fun <D: NavDestination> NavDestinationBuilder<D>.setupNav()

    /**
     * Used when creating navigation graph
     */
    inline fun <reified T: DialogFragment> addNavigationRoute(navGraphBuilder: NavGraphBuilder, context: Context) {
        navGraphBuilder.apply {
            // default fragment
            dialog<T>(routeDefinition) {
                label = getLabel(context)
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
abstract class SimpleNavDialogRoute(override val routeDefinition: String, val labelId: Int? = null) : NavDialogRoute() {
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
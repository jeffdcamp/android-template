@file:Suppress("MemberVisibilityCanBePrivate")

package org.jdc.template.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

abstract class NavComposeRoute {
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

    abstract fun getArguments(): List<NamedNavArgument>
    open fun getDeepLinks(): List<NavDeepLink> = emptyList()

    /**
     * Used when creating navigation graph
     */
    fun addNavigationRoute(navGraphBuilder: NavGraphBuilder, content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit) {
        navGraphBuilder.composable(
            route = routeDefinition,
            arguments = getArguments(),
            deepLinks = getDeepLinks(),
            content = content
        )
    }
}

/**
 * Simple Route that requires no arguments
 */
@Suppress("UnnecessaryAbstractClass") // this is an edge case... allow
abstract class SimpleNavComposeRoute(override val routeDefinition: String) : NavComposeRoute() {
    /**
     * Route used when navigating
     */
    fun createRoute(): String = routeDefinition

    override fun getArguments(): List<NamedNavArgument> = emptyList()

    override fun getDeepLinks(): List<NavDeepLink> = emptyList()
}
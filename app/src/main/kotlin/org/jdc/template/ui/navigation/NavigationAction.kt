@file:Suppress("unused")

package org.jdc.template.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import co.touchlab.kermit.Logger

sealed interface NavigationActionRoute : NavigationAction {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean
}

sealed interface NavigationActionIntent : NavigationAction {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(context: Context, resetNavigate: (NavigationAction) -> Unit): Boolean
}

sealed interface NavigationActionFull : NavigationAction {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(context: Context, navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean
}


sealed interface NavigationAction {
    data class Navigate(private val route: NavRoute) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            navController.navigate(route.value)

            resetNavigate(this)
            return false
        }
    }

    data class NavigateMultiple(val routes: List<NavRoute>) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            routes.forEach { route ->
                navController.navigate(route.value)
            }

            resetNavigate(this)
            return false
        }
    }

    data class NavigateWithOptions(val route: NavRoute, val navOptions: NavOptions) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            navController.navigate(route.value, navOptions)

            resetNavigate(this)
            return false
        }
    }

    data class NavigateIntent(private val intent: Intent, private val options: Bundle? = null) : NavigationActionIntent {
        override fun navigate(context: Context, resetNavigate: (NavigationAction) -> Unit): Boolean {
            try {
                context.startActivity(intent, options)
            } catch (ignore: Exception) {
                Logger.e(ignore) { "Failed to startActivity for intent (${intent.data})" }
            }
            resetNavigate(this)
            return false
        }
    }

    data class PopAndNavigate(private val route: NavRoute) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route.value)

            resetNavigate(this)
            return stackPopped
        }
    }

    data class PopAndNavigateIntent(private val intent: Intent, private val options: Bundle? = null) : NavigationActionFull {
        override fun navigate(context: Context, navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val stackPopped = navController.popBackStack()
            try {
                context.startActivity(intent, options)
            } catch (ignore: Exception) {
                Logger.e(ignore) { "Failed to startActivity for intent (${intent.data})" }
            }
            resetNavigate(this)
            return stackPopped
        }
    }

    data class Pop(private val popToRouteDefinition: NavRouteDefinition? = null, private val inclusive: Boolean = false) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val stackPopped = if (popToRouteDefinition == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(popToRouteDefinition.value, inclusive = inclusive)
            }

            resetNavigate(this)
            return stackPopped
        }
    }

    data class PopWithResult(
        private val resultValues: List<PopResultKeyValue>,
        private val popToRouteDefinition: NavRouteDefinition? = null,
        private val inclusive: Boolean = false,
    ) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val destinationNavBackStackEntry = if (popToRouteDefinition != null) {
                navController.getBackStackEntry(popToRouteDefinition.value)
            } else {
                navController.previousBackStackEntry
            }
            resultValues.forEach { destinationNavBackStackEntry?.savedStateHandle?.set(it.key, it.value) }
            val stackPopped = if (popToRouteDefinition == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(popToRouteDefinition.value, inclusive = inclusive)
            }

            resetNavigate(this)
            return stackPopped
        }
    }
}

data class PopResultKeyValue(val key: String, val value: Any)

fun NavigationAction.navigate(context: Context, navController: NavController, resetNavigate: (NavigationAction) -> Unit) {
    when(this) {
        is NavigationActionIntent -> navigate(context, resetNavigate)
        is NavigationActionRoute -> navigate(navController, resetNavigate)
        is NavigationActionFull -> navigate(context, navController, resetNavigate)
    }
}

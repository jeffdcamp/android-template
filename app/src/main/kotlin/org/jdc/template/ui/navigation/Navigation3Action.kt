@file:Suppress("unused")

package org.jdc.template.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation3.runtime.NavKey
import co.touchlab.kermit.Logger
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator

sealed interface Navigation3ActionRoute : Navigation3Action {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean
}

sealed interface Navigation3ActionIntent : Navigation3Action {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(context: Context, resetNavigate: (Navigation3Action) -> Unit): Boolean
}

sealed interface Navigation3ActionFull : Navigation3Action {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean
}


sealed interface Navigation3Action {
    data class Navigate(private val route: NavKey) : Navigation3ActionRoute {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            navigator.navigate(route)
            resetNavigate(this)
            return false
        }
    }

    data class NavigateMultiple(private val routes: List<NavKey>) : Navigation3ActionRoute {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            navigator.navigate(routes)
            resetNavigate(this)
            return false
        }
    }

    data class NavigateIntent(private val context: Context, private val intent: Intent, private val options: Bundle? = null) : Navigation3ActionIntent {
        override fun navigate(context: Context, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            try {
                context.startActivity(intent, options)
            } catch (ignore: Exception) {
                Logger.e(ignore) { "Failed to startActivity for intent (${intent.data})" }
            }
            resetNavigate(this)
            return false
        }
    }

    data class NavigateWithBackstack(
        private val block: (navigator: Navigation3Navigator) -> Unit,
    ) : Navigation3ActionRoute {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            block(navigator)
            resetNavigate(this)
            return false
        }
    }

    data class PopAndNavigate(private val route: NavKey) : Navigation3ActionRoute {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            val stackPopped = navigator.popAndNavigate(route)

            resetNavigate(this)
            return stackPopped
        }
    }

    /**
     * Handle custom cases, such as OS specific Intents
     */
    data class PopAndNavigateIntent(private val context: Context, private val intent: Intent, private val options: Bundle? = null) : Navigation3ActionFull {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            val stackPopped = navigator.pop()
            try {
                context.startActivity(intent, options)
            } catch (ignore: Exception) {
                Logger.e(ignore) { "Failed to startActivity for intent (${intent.data})" }
            }
            resetNavigate(this)
            return stackPopped
        }
    }

    data class Pop(
        private val route: NavKey? = null,
    ) : Navigation3ActionRoute {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            val stackPopped = navigator.pop(route)

            resetNavigate(this)
            return stackPopped
        }
    }

    data class PopWithBackstack(
        private val block: (navigator: Navigation3Navigator) -> Boolean,
    ) : Navigation3ActionRoute {
        override fun navigate(navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit): Boolean {
            val stackPopped = block(navigator)
            resetNavigate(this)
            return stackPopped
        }
    }
}

data class PopResultKeyValue(val key: String, val value: Any)

fun Navigation3Action.navigate(context: Context, navigator: Navigation3Navigator, resetNavigate: (Navigation3Action) -> Unit) {
    when(this) {
        is Navigation3ActionIntent -> navigate(context, resetNavigate)
        is Navigation3ActionRoute -> navigate(navigator, resetNavigate)
        is Navigation3ActionFull -> navigate(navigator, resetNavigate)
    }
}

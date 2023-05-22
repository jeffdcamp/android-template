package org.jdc.template.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Used for typical ViewModels that have navigation
 */
interface ViewModelNav {
    val navigatorFlow: StateFlow<ViewModelNavigator?>

    fun navigate(route: String, popBackStack: Boolean = false)
    fun navigate(routes: List<String>)
    fun navigate(route: String, navOptions: NavOptions)
    fun navigate(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit)
    fun navigate(intent: Intent, options: Bundle? = null, popBackStack: Boolean = false)
    fun popBackStack(popToRouteDefinition: String? = null, inclusive: Boolean = false)
    fun popBackStackWithResult(popToRouteDefinition: String? = null, inclusive: Boolean = false, resultValues: List<PopResultKeyValue>)
    fun navigate(viewModelNavigator: ViewModelNavigator)
    fun resetNavigate(viewModelNavigator: ViewModelNavigator)
}

class ViewModelNavImpl : ViewModelNav {
    private val _navigatorFlow = MutableStateFlow<ViewModelNavigator?>(null)
    override val navigatorFlow: StateFlow<ViewModelNavigator?> = _navigatorFlow.asStateFlow()

    override fun navigate(route: String, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) ViewModelNavigator.PopAndNavigate(route) else ViewModelNavigator.Navigate(route))
    }

    override fun navigate(routes: List<String>) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.NavigateMultiple(routes))
    }

    override fun navigate(route: String, navOptions: NavOptions) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.NavigateWithOptions(route, navOptions))
    }

    override fun navigate(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.NavigateWithOptions(route, navOptions(optionsBuilder)))
    }

    override fun navigate(intent: Intent, options: Bundle?, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) ViewModelNavigator.PopAndNavigateIntent(intent, options) else ViewModelNavigator.NavigateIntent(intent, options))
    }

    override fun popBackStack(popToRouteDefinition: String?, inclusive: Boolean) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.Pop(popToRouteDefinition, inclusive))
    }

    override fun popBackStackWithResult(popToRouteDefinition: String?, inclusive: Boolean, resultValues: List<PopResultKeyValue>) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.PopWithResult(popToRouteDefinition, inclusive, resultValues))
    }

    override fun navigate(viewModelNavigator: ViewModelNavigator) {
        _navigatorFlow.compareAndSet(null, viewModelNavigator)
    }

    override fun resetNavigate(viewModelNavigator: ViewModelNavigator) {
        _navigatorFlow.compareAndSet(viewModelNavigator, null)
    }
}

sealed class ViewModelNavigator {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    abstract fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean

    class Navigate(private val route: String) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateMultiple(private val routes: List<String>) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            routes.forEach { route ->
                navController.navigate(route)
            }

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateWithOptions(private val route: String, private val navOptions: NavOptions) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            navController.navigate(route, navOptions)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateIntent(val intent: Intent, val options: Bundle? = null) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            context.startActivity(intent, options)
            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class PopAndNavigate(private val route: String) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }

    class PopAndNavigateIntent(val intent: Intent, val options: Bundle? = null) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = navController.popBackStack()
            context.startActivity(intent, options)
            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }

    class Pop(private val popToRouteDefinition: String? = null, private val inclusive: Boolean = false) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = if (popToRouteDefinition == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(popToRouteDefinition, inclusive = inclusive)
            }

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }

    class PopWithResult(private val popToRouteDefinition: String? = null, private val inclusive: Boolean = false, private val resultValues: List<PopResultKeyValue>) : ViewModelNavigator() {
        override fun navigate(context: Context, navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val destinationNavBackStackEntry = if (popToRouteDefinition != null) {
                navController.getBackStackEntry(popToRouteDefinition)
            } else {
                navController.previousBackStackEntry
            }
            resultValues.forEach { destinationNavBackStackEntry?.savedStateHandle?.set(it.key, it.value) }
            val stackPopped = if (popToRouteDefinition == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(popToRouteDefinition, inclusive = inclusive)
            }

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }
}

data class PopResultKeyValue(val key: String, val value: Any)

@Composable
fun HandleNavigation(
    viewModelNav: ViewModelNav,
    navController: NavController?
) {
    navController ?: return
    val navigator by viewModelNav.navigatorFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(navigator) {
        navigator?.navigate(context, navController, viewModelNav)
    }
}

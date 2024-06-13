package org.jdc.template.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jdc.template.util.log.CrashLogException

/**
 * Used for MainScreen ViewModels that that have bottom NavigationBars or NavigationRails
 */
interface ViewModelNavigationBar<T : Enum<T>> {
    val navigatorFlow: StateFlow<ViewModelNavBarNavigator?>
    val selectedNavBarFlow: StateFlow<T?>

    fun navigate(route: NavigationRoute, popBackStack: Boolean = false)
    fun navigate(routes: List<NavigationRoute>)
    fun navigate(route: NavigationRoute, navOptions: NavOptions)
    fun navigate(route: NavigationRoute, optionsBuilder: NavOptionsBuilder.() -> Unit)
    fun navigate(intent: Intent, options: Bundle? = null)
    fun onNavBarItemSelected(selectedItem: T, route: NavigationRoute? = null)
    fun navBarNavigation(route: NavigationRoute, reselected: Boolean)
    fun resetNavigate(viewModelNavBarNavigator: ViewModelNavBarNavigator)
}

class ViewModelNavigationBarImpl<T : Enum<T>>(
    startNavBarItem: T?,
    private val navigationBarConfig: NavigationBarConfig<T>? = null,
) : ViewModelNavigationBar<T> {
    private val _navigatorFlow = MutableStateFlow<ViewModelNavBarNavigator?>(null)
    override val navigatorFlow: StateFlow<ViewModelNavBarNavigator?> = _navigatorFlow.asStateFlow()

    private val _selectedNavBarFlow = MutableStateFlow<T?>(startNavBarItem)
    override val selectedNavBarFlow: StateFlow<T?> = _selectedNavBarFlow.asStateFlow()

    override fun navigate(route: NavigationRoute, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) ViewModelNavBarNavigator.PopAndNavigate(route) else ViewModelNavBarNavigator.Navigate(route))
    }

    override fun navigate(routes: List<NavigationRoute>) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateMultiple(routes))
    }

    override fun navigate(route: NavigationRoute, navOptions: NavOptions) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateWithOptions(route, navOptions))
    }

    override fun navigate(route: NavigationRoute, optionsBuilder: NavOptionsBuilder.() -> Unit) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateWithOptions(route, navOptions(optionsBuilder)))
    }

    override fun navigate(intent: Intent, options: Bundle?) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateIntent(intent, options))
    }

    override fun navBarNavigation(route: NavigationRoute, reselected: Boolean) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavBarNavigate(route, reselected))
    }

    override fun resetNavigate(viewModelNavBarNavigator: ViewModelNavBarNavigator) {
        _navigatorFlow.compareAndSet(viewModelNavBarNavigator, null)
    }

    override fun onNavBarItemSelected(selectedItem: T, route: NavigationRoute?) {
        val navRoute = route ?: navigationBarConfig?.getRouteByNavItem(selectedItem)

        if (navRoute != null) {
            val reselected = _selectedNavBarFlow.value == selectedItem
            navBarNavigation(navRoute, reselected)
            _selectedNavBarFlow.value = selectedItem
        } else {
            Logger.e(CrashLogException()) {
                "route not found for selectedItem [$selectedItem].  Make sure either the selectedItem is defined in the NavBarConfig OR the 'route' is supplied to this function"
            }
        }
    }
}

sealed class ViewModelNavBarNavigator {
    abstract fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean

    class NavBarNavigate(private val route: NavigationRoute, private val reselected: Boolean) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean {
            if (reselected) {
                // clear back stack
                navController.popBackStack(route, inclusive = false)
            }

            navController.navigate(route) {
                // Avoid multiple copies of the same destination when reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class Navigate(private val route: NavigationRoute) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean {
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateMultiple(private val routes: List<NavigationRoute>) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean {
            routes.forEach { route ->
                navController.navigate(route)
            }

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateWithOptions(private val route: NavigationRoute, private val navOptions: NavOptions) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean {
            navController.navigate(route, navOptions)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateIntent(val intent: Intent, val options: Bundle? = null) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean {
            try {
                context.startActivity(intent, options)
            } catch (ignore: Exception) {
                Logger.e(ignore) { "Failed to startActivity for intent (${intent.data})" }
            }
            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class PopAndNavigate(private val route: NavigationRoute) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigationBar<T>): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }
}

interface NavigationBarConfig<T : Enum<T>> {
    fun getRouteByNavItem(navBarItem: T): NavigationRoute?
}

class DefaultNavigationBarConfig<T : Enum<T>>(
    private val navBarItemRouteMap: Map<T, NavigationRoute>,
) : NavigationBarConfig<T> {
    override fun getRouteByNavItem(navBarItem: T): NavigationRoute? = navBarItemRouteMap[navBarItem]
}

@Composable
fun <T : Enum<T>> HandleNavBarNavigation(
    viewModelNavigationBar: ViewModelNavigationBar<T>,
    navController: NavController?
) {
    navController ?: return
    val navigator by viewModelNavigationBar.navigatorFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(navigator) {
        navigator?.navigate(context, navController, viewModelNavigationBar)
    }
}

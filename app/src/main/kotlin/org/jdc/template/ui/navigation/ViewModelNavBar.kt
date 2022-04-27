package org.jdc.template.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

/**
 * Used for MainScreen ViewModels that that have bottom NavigationBars or NavigationRails
 */
interface ViewModelNavBar<T : Enum<T>> {
    val navigatorFlow: StateFlow<ViewModelNavBarNavigator?>
    val selectedNavBarFlow: StateFlow<T?>

    fun navigate(route: String, popBackStack: Boolean = false)
    fun navigate(routes: List<String>)
    fun navigate(route: String, navOptions: NavOptions)
    fun navigate(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit)
    fun navigate(intent: Intent, options: Bundle? = null)
    fun onNavBarItemSelected(selectedItem: T, route: String? = null)
    fun navBarNavigation(route: String, reselected: Boolean)
    fun resetNavigate(viewModelNavBarNavigator: ViewModelNavBarNavigator)
}

class ViewModelNavBarImpl<T : Enum<T>>(
    startNavBarItem: T?,
    private val navBarConfig: NavBarConfig<T>? = null,
) : ViewModelNavBar<T> {
    private val _navigatorFlow = MutableStateFlow<ViewModelNavBarNavigator?>(null)
    override val navigatorFlow: StateFlow<ViewModelNavBarNavigator?> = _navigatorFlow.asStateFlow()

    private val _selectedNavBarFlow = MutableStateFlow<T?>(startNavBarItem)
    override val selectedNavBarFlow: StateFlow<T?> = _selectedNavBarFlow.asStateFlow()

    override fun navigate(route: String, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) ViewModelNavBarNavigator.PopAndNavigate(route) else ViewModelNavBarNavigator.Navigate(route))
    }

    override fun navigate(routes: List<String>) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateMultiple(routes))
    }

    override fun navigate(route: String, navOptions: NavOptions) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateWithOptions(route, navOptions))
    }

    override fun navigate(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateWithOptions(route, navOptions(optionsBuilder)))
    }

    override fun navigate(intent: Intent, options: Bundle?) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateIntent(intent, options))
    }

    override fun navBarNavigation(route: String, reselected: Boolean) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavBarNavigate(route, reselected))
    }

    override fun resetNavigate(viewModelNavBarNavigator: ViewModelNavBarNavigator) {
        _navigatorFlow.compareAndSet(viewModelNavBarNavigator, null)
    }

    override fun onNavBarItemSelected(selectedItem: T, route: String?) {
        val navRoute = route ?: navBarConfig?.getRouteByNavItem(selectedItem)

        if (navRoute != null) {
            val reselected = _selectedNavBarFlow.value == selectedItem
            navBarNavigation(navRoute, reselected)
            _selectedNavBarFlow.value = selectedItem
        } else {
            Timber.e("route not found for selectedItem [$selectedItem].  Make sure either the selectedItem is defined in the NavBarConfig OR the 'route' is supplied to this function")
        }
    }
}

sealed class ViewModelNavBarNavigator {
    abstract fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean

    class NavBarNavigate(private val route: String, private val reselected: Boolean) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean {
            if (reselected) {
                // clear back stack
                val popped = navController.popBackStack(route, inclusive = false)
                if (!popped && routeHasArgs(route)) {
                    // try again if route contains args (navController.popBackStack(...) does not support routes with args)
                    navController.popBackStackRouteWithArgs(route)
                }
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

    class Navigate(private val route: String) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean {
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateMultiple(private val routes: List<String>) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean {
            routes.forEach { route ->
                navController.navigate(route)
            }

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateWithOptions(private val route: String, private val navOptions: NavOptions) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean {
            navController.navigate(route, navOptions)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateIntent(val intent: Intent, val options: Bundle? = null) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean {
            context.startActivity(intent, options)
            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class PopAndNavigate(private val route: String) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavBar<T>): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }
}

interface NavBarConfig<T : Enum<T>> {
    fun getRouteByNavItem(navBarItem: T): String?
}

class DefaultNavBarConfig<T : Enum<T>>(
    private val navBarItemRouteMap: Map<T, String>,
) : NavBarConfig<T> {
    override fun getRouteByNavItem(navBarItem: T): String? = navBarItemRouteMap[navBarItem]
}

private fun routeHasArgs(route: String): Boolean {
    return (route.contains('/') || route.contains('?'))
}

/**
 * Alternative popBackStack that works with arguments (popBackStack() does not support routes with args)
 * https://stackoverflow.com/questions/71948142/jetpack-compose-how-to-do-popbackstack-with-arguments
 * "Note that this does not support the inclusive and saveState options (due to the current navigation API limitation),
 * so you can't restoreState when you return to the current destination." (this should not be an issue for the use-case of removing the full backstack
 * for a navigation item)
 */
private fun NavController.popBackStackRouteWithArgs(route: String): Boolean {
    if (backQueue.isEmpty()) {
        return false
    }

    var found = false
    var popCount = 0
    val iterator = backQueue.reversed().iterator()
    while (iterator.hasNext()) {
        val entry = iterator.next()
        popCount++
        val intent = entry.arguments?.get("android-support-nav:controller:deepLinkIntent") as Intent?
        if (intent?.data?.toString() == "android-app://androidx.navigation/$route") {
            found = true
            break
        }
    }

    if (found) {
        navigate(route) {
            while (popCount-- > 0) {
                popBackStack()
            }
        }
    }
    return found
}

@Composable
fun <T : Enum<T>> HandleNavBarNavigation(
    viewModelNavBar: ViewModelNavBar<T>,
    navController: NavController?
) {
    navController ?: return
    val navigator by viewModelNavBar.navigatorFlow.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(navigator) {
        navigator?.navigate(context, navController, viewModelNavBar)
    }
}

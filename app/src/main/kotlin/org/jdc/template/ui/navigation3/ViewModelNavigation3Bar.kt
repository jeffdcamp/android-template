package org.jdc.template.ui.navigation3

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jdc.template.shared.util.log.CrashLogException
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator

/**
 * Used for MainScreen ViewModels that that have bottom NavigationBars or NavigationRails
 */
interface ViewModelNavigation3Bar<T : Enum<T>> {
    val navBarNavigatorFlow: StateFlow<ViewModelNavBarNavigator?>
    val selectedNavBarFlow: StateFlow<T?>

    fun navigate(route: NavKey, popBackStack: Boolean = false)
    fun navigate(routes: List<NavKey>)
//    fun navigate(intent: Intent, options: Bundle? = null)
    fun onNavBarItemSelected(selectedItem: T, route: NavKey? = null)
    fun navBarNavigation(route: NavKey, reselected: Boolean)
    fun resetNavigate(viewModelNavBarNavigator: ViewModelNavBarNavigator)
}

class ViewModelNavigation3BarImpl<T : Enum<T>>(
    startNavBarItem: T?,
    private val navigationBarConfig: Navigation3BarConfig<T>? = null,
) : ViewModelNavigation3Bar<T> {
    private val _navigatorFlow = MutableStateFlow<ViewModelNavBarNavigator?>(null)
    override val navBarNavigatorFlow: StateFlow<ViewModelNavBarNavigator?> = _navigatorFlow.asStateFlow()

    private val _selectedNavBarFlow = MutableStateFlow<T?>(startNavBarItem)
    override val selectedNavBarFlow: StateFlow<T?> = _selectedNavBarFlow.asStateFlow()

    override fun navigate(route: NavKey, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) ViewModelNavBarNavigator.PopAndNavigate(route) else ViewModelNavBarNavigator.Navigate(route))
    }

    override fun navigate(routes: List<NavKey>) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateMultiple(routes))
    }

//    override fun navigate(intent: Intent, options: Bundle?) {
//        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavigateIntent(intent, options))
//    }

    override fun navBarNavigation(route: NavKey, reselected: Boolean) {
        _navigatorFlow.compareAndSet(null, ViewModelNavBarNavigator.NavBarNavigate(route, reselected))
    }

    override fun resetNavigate(viewModelNavBarNavigator: ViewModelNavBarNavigator) {
        _navigatorFlow.compareAndSet(viewModelNavBarNavigator, null)
    }

    override fun onNavBarItemSelected(selectedItem: T, route: NavKey?) {
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
    abstract fun <T : Enum<T>> navigate(context: Context, navigator: Navigation3Navigator<NavKey>, viewModelNav: ViewModelNavigation3Bar<T>): Boolean

    class NavBarNavigate(private val route: NavKey, private val reselected: Boolean) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navigator: Navigation3Navigator<NavKey>, viewModelNav: ViewModelNavigation3Bar<T>): Boolean {
            navigator.navigateTopLevel(route, reselected)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class Navigate(private val route: NavKey) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navigator: Navigation3Navigator<NavKey>, viewModelNav: ViewModelNavigation3Bar<T>): Boolean {
            navigator.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateMultiple(private val routes: List<NavKey>) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navigator: Navigation3Navigator<NavKey>, viewModelNav: ViewModelNavigation3Bar<T>): Boolean {
            navigator.navigate(routes)
            viewModelNav.resetNavigate(this)
            return false
        }
    }

//    class NavigateIntent(val context: Context, val intent: Intent, val options: Bundle? = null) : ViewModelNavBarNavigator() {
//        override fun <T : Enum<T>> navigate(context: Context, navController: NavController, viewModelNav: ViewModelNavigation3Bar<T>): Boolean {
//            try {
//                context.startActivity(intent, options)
//            } catch (ignore: Exception) {
//                Logger.e(ignore) { "Failed to startActivity for intent (${intent.data})" }
//            }
//            viewModelNav.resetNavigate(this)
//            return false
//        }
//    }

    class PopAndNavigate(private val route: NavKey) : ViewModelNavBarNavigator() {
        override fun <T : Enum<T>> navigate(context: Context, navigator: Navigation3Navigator<NavKey>, viewModelNav: ViewModelNavigation3Bar<T>): Boolean {
            val stackPopped = navigator.popAndNavigate(route)
            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }
}



interface Navigation3BarConfig<T : Enum<T>> {
    fun getRouteByNavItem(navBarItem: T): NavKey?
}

class DefaultNavigation3BarConfig<T : Enum<T>>(
    private val navBarItemRouteMap: Map<T, NavKey>,
) : Navigation3BarConfig<T> {
    override fun getRouteByNavItem(navBarItem: T): NavKey? = navBarItemRouteMap[navBarItem]
}

@Composable
fun <T : Enum<T>> HandleNav3BarNavigation(
    viewModelNavigationBar: ViewModelNavigation3Bar<T>,
    navigator: Navigation3Navigator<NavKey>,
) {
    val navBarNavigator by viewModelNavigationBar.navBarNavigatorFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(navigator) {
        navBarNavigator?.navigate(
            context = context,
            navigator = navigator,
            viewModelNav = viewModelNavigationBar
        )
    }
}

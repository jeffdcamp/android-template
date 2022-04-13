package org.jdc.template.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ViewModelNav {
    val navigatorFlow: StateFlow<ViewModelNavigator?>

    fun navigate(route: String, popBackStack: Boolean = false)
    fun navigate(routes: List<String>)
    fun navigateWithOptions(route: String, navOptions: NavOptions)
    fun navigateWithOptions(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit)
    fun popBackStack(popToRoute: String? = null, inclusive: Boolean = false)
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

    override fun navigateWithOptions(route: String, navOptions: NavOptions) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.NavigateWithOptions(route, navOptions))
    }

    override fun navigateWithOptions(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.NavigateWithOptions(route, navOptions(optionsBuilder)))
    }

    override fun popBackStack(popToRoute: String?, inclusive: Boolean) {
        _navigatorFlow.compareAndSet(null, ViewModelNavigator.Pop(popToRoute, inclusive))
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
    abstract fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean

    class Navigate(private val route: String) : ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateMultiple(private val routes: List<String>): ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            routes.forEach { route ->
                navController.navigate(route)
            }

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class NavigateWithOptions(private val route: String, private val navOptions: NavOptions) : ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            navController.navigate(route, navOptions)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class PopAndNavigate(private val route: String) : ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }

    class Pop(private val popToRoute: String?, private val inclusive: Boolean = false) : ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = if (popToRoute == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(popToRoute, inclusive = inclusive)
            }

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }
}

@Composable
fun HandleNavigation(
    viewModelNav: ViewModelNav,
    navController: NavController,
    navigatorFlow: StateFlow<ViewModelNavigator?>,
) {
    val navigator by navigatorFlow.collectAsState()
    navigator?.navigate(navController, viewModelNav)
}
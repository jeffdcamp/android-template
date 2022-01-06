package org.jdc.template.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ViewModelNav {
    val navigateRouteFlow: StateFlow<ViewModelNavigator?>

    fun navigate(route: String, popBackStack: Boolean = false)
    fun popBackStack(popToRoute: String? = null, inclusive: Boolean = false)
    fun navigate(viewModelNavigator: ViewModelNavigator)
    fun resetNavigate(viewModelNavigator: ViewModelNavigator)
}

class ViewModelNavImpl : ViewModelNav {
    private val _navigateRouteFlow = MutableStateFlow<ViewModelNavigator?>(null)
    override val navigateRouteFlow: StateFlow<ViewModelNavigator?> = _navigateRouteFlow.asStateFlow()

    override fun navigate(route: String, popBackStack: Boolean) {
        _navigateRouteFlow.compareAndSet(null, if (popBackStack) ViewModelNavigator.PopAndNavigate(route) else ViewModelNavigator.Navigate(route))
    }

    override fun popBackStack(popToRoute: String?, inclusive: Boolean) {
        _navigateRouteFlow.compareAndSet(null, ViewModelNavigator.Pop(popToRoute, inclusive))
    }

    override fun navigate(viewModelNavigator: ViewModelNavigator) {
        _navigateRouteFlow.compareAndSet(null, viewModelNavigator)
    }

    override fun resetNavigate(viewModelNavigator: ViewModelNavigator) {
        _navigateRouteFlow.compareAndSet(viewModelNavigator, null)
    }
}

sealed class ViewModelNavigator {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    abstract fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean

    class Navigate(private val route: String): ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class PopAndNavigate(private val route: String): ViewModelNavigator() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }

    class Pop(private val popToRoute: String?, private val inclusive: Boolean = false): ViewModelNavigator() {
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
    navController: NavController?,
    navigatorFlow: StateFlow<ViewModelNavigator?>
) {
    val navigator by navigatorFlow.collectAsState()

    if (navController != null) {
        navigator?.navigate(navController, viewModelNav)
    }
}
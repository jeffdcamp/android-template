package org.jdc.template.ui

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ViewModelNav {
    val navigateRouteFlow: StateFlow<ViewModelNavType?>

    fun navigate(route: String, popBackStack: Boolean = false)
    fun popBackStack(popToRoute: String? = null, inclusive: Boolean = false)
    fun navigate(navigationData: ViewModelNavType)
    fun resetNavigate(navigationData: ViewModelNavType)
}

class ViewModelNavImpl : ViewModelNav {
    private val _navigateRouteFlow = MutableStateFlow<ViewModelNavType?>(null)
    override val navigateRouteFlow: StateFlow<ViewModelNavType?> = _navigateRouteFlow.asStateFlow()

    override fun navigate(route: String, popBackStack: Boolean) {
        _navigateRouteFlow.compareAndSet(null, if (popBackStack) ViewModelNavType.PopAndNavigate(route) else ViewModelNavType.Navigate(route))
    }

    override fun popBackStack(popToRoute: String?, inclusive: Boolean) {
        _navigateRouteFlow.compareAndSet(null, ViewModelNavType.Pop(popToRoute, inclusive))
    }

    override fun navigate(navigationData: ViewModelNavType) {
        _navigateRouteFlow.compareAndSet(null, navigationData)
    }

    override fun resetNavigate(navigationData: ViewModelNavType) {
        _navigateRouteFlow.compareAndSet(navigationData, null)
    }
}

sealed class ViewModelNavType {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    abstract fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean

    class Navigate(private val route: String): ViewModelNavType() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return false
        }
    }

    class PopAndNavigate(private val route: String): ViewModelNavType() {
        override fun navigate(navController: NavController, viewModelNav: ViewModelNav): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            viewModelNav.resetNavigate(this)
            return stackPopped
        }
    }

    class Pop(private val popToRoute: String?, private val inclusive: Boolean = false): ViewModelNavType() {
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
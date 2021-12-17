package org.jdc.template.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel: ViewModel(){
    private val _navigateRouteFlow = MutableStateFlow<NavigationData?>(null)
    val navigateRouteFlow: StateFlow<NavigationData?> = _navigateRouteFlow.asStateFlow()

    fun navigate(route: String) {
        _navigateRouteFlow.compareAndSet(null, NavigationData(route))
    }

    fun popBackstack() {
        _navigateRouteFlow.compareAndSet(null, NavigationData(popBackStack = true))
    }

    fun navigate(navigationData: NavigationData) {
        _navigateRouteFlow.compareAndSet(null, navigationData)
    }

    fun resetNavigate(navigationData: NavigationData) {
        _navigateRouteFlow.compareAndSet(navigationData, null)
    }
}

data class NavigationData(
    val route: String? = null,
    val popBackStack: Boolean = false
) {
    fun navigate(navController: NavController, baseViewModel: BaseViewModel) {
        if (popBackStack) {
            navController.popBackStack()
        }
        route?.let { navController.navigate(it) }
        baseViewModel.resetNavigate(this)
    }

    fun navigate(fragment: Fragment, navController: NavController, baseViewModel: BaseViewModel) {
        if (popBackStack) {
            if (!navController.popBackStack()) {
                fragment.requireActivity().finish()
            }
        }
        route?.let { navController.navigate(it) }
        baseViewModel.resetNavigate(this)
    }
}
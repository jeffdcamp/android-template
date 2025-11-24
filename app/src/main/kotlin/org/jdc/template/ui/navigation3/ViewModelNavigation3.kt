package org.jdc.template.ui.navigation3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator

/**
 * Used for typical ViewModels that have navigation
 */
interface ViewModelNavigation3 {
    val navigationActionFlow: StateFlow<Navigation3Action?>

    fun navigate(route: NavKey, popBackStack: Boolean = false)
    fun navigate(routes: List<NavKey>)
    fun navigate(route: NavKey)
    fun navigateWithBackStack(block: (navigator: Navigation3Navigator) -> Unit)
    fun popBackStack(route: NavKey? = null)
    fun popWithBackStack(block: (navigator: Navigation3Navigator) -> Boolean)

    fun navigate(context: Context, intent: Intent, options: Bundle? = null, popBackStack: Boolean = false)

    fun navigate(navigationAction: Navigation3Action)
    fun resetNavigate(navigationAction: Navigation3Action)
}

class ViewModelNavigation3Impl : ViewModelNavigation3 {
    private val _navigatorFlow = MutableStateFlow<Navigation3Action?>(null)
    override val navigationActionFlow: StateFlow<Navigation3Action?> = _navigatorFlow.asStateFlow()

    override fun navigate(route: NavKey, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) Navigation3Action.PopAndNavigate(route) else Navigation3Action.Navigate(route))
    }

    override fun navigate(routes: List<NavKey>) {
        _navigatorFlow.compareAndSet(null, Navigation3Action.NavigateMultiple(routes))
    }

    override fun navigate(route: NavKey) {
        _navigatorFlow.compareAndSet(null, Navigation3Action.Navigate(route))
    }

    override fun navigateWithBackStack(block: (navigator: Navigation3Navigator) -> Unit) {
     _navigatorFlow.compareAndSet(null, Navigation3Action.NavigateWithBackstack(block))
    }

    override fun popBackStack(route: NavKey?) {
        _navigatorFlow.compareAndSet(null, Navigation3Action.Pop(route))
    }

    override fun popWithBackStack(block: (navigator: Navigation3Navigator) -> Boolean) {
        _navigatorFlow.compareAndSet(null, Navigation3Action.PopWithBackstack(block))
    }

    override fun navigate(context: Context, intent: Intent, options: Bundle?, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) Navigation3Action.PopAndNavigateIntent(context, intent, options) else Navigation3Action.NavigateIntent(context, intent, options))
    }

    override fun navigate(navigationAction: Navigation3Action) {
        _navigatorFlow.compareAndSet(null, navigationAction)
    }

    override fun resetNavigate(navigationAction: Navigation3Action) {
        _navigatorFlow.compareAndSet(navigationAction, null)
    }
}

@Composable
fun HandleNavigation3(
    viewModelNavigation: ViewModelNavigation3,
    navigator: Navigation3Navigator
) {
    val navigationActionState = viewModelNavigation.navigationActionFlow.collectAsStateWithLifecycle()
    val navigationAction = navigationActionState.value

    val context = LocalContext.current
    LaunchedEffect(navigationAction) {
        navigationAction?.navigate(context = context, navigator = navigator, resetNavigate = viewModelNavigation::resetNavigate)
    }
}

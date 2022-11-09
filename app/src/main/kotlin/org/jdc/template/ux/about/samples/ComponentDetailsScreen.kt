package org.jdc.template.ux.about.samples

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.NavComposeRoute
import org.jdc.template.ui.navigation.RouteUtil
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.delegates.requireSavedState
import org.jdc.template.ux.MainAppScaffoldWithNavBar
import javax.inject.Inject

object ComponentDetailsRoute : NavComposeRoute() {
    override val routeDefinition: String = "componentDetails/${RouteUtil.defineArg(Arg.COMPONENT_NAME)}"

    fun createRoute(componentName: String): String {
        return "componentDetails/$componentName"
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.COMPONENT_NAME) {
                type = NavType.StringType
            }
        )
    }

    object Arg {
        const val COMPONENT_NAME = "componentName"
    }
}

@HiltViewModel
class ComponentDetailsViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    val componentName: String by requireSavedState(savedStateHandle, ComponentDetailsRoute.Arg.COMPONENT_NAME)
}

@Composable
fun ComponentDetailsScreen(
    navController: NavController,
    viewModel: ComponentDetailsViewModel = hiltViewModel()
) {
    val componentName = viewModel.componentName
    val component: Component = Components.first { it.name == componentName }

    MainAppScaffoldWithNavBar(
        title = componentName,
        onNavigationClick = { navController.popBackStack() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(component.examples) { example ->
                ListItem(
                    headlineText = { Text(example.name) },
                    modifier = Modifier.clickable {
                        navController.navigate(ComponentExampleRoute.createRoute(componentName, example.name))
                    },
                )
            }
        }
    }
}
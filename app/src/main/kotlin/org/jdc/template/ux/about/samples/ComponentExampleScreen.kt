package org.jdc.template.ux.about.samples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

object ComponentExampleRoute : NavComposeRoute() {
    override val routeDefinition: String = "componentExample/${RouteUtil.defineArg(Arg.COMPONENT_NAME)}/${RouteUtil.defineArg(Arg.EXAMPLE_NAME)}"

    fun createRoute(componentName: String, exampleName: String): String {
        return "componentExample/$componentName/$exampleName"
    }

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(Arg.COMPONENT_NAME) {
                type = NavType.StringType
            },
            navArgument(Arg.EXAMPLE_NAME) {
                type = NavType.StringType
            }
        )
    }

    object Arg {
        const val COMPONENT_NAME = "componentName"
        const val EXAMPLE_NAME = "exampleName"
    }
}

@HiltViewModel
class ComponentExampleViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    val componentName: String by requireSavedState(savedStateHandle, ComponentExampleRoute.Arg.COMPONENT_NAME)
    val exampleName: String by requireSavedState(savedStateHandle, ComponentExampleRoute.Arg.EXAMPLE_NAME)
}

@Composable
fun ComponentExampleScreen(
    navController: NavController,
    viewModel: ComponentExampleViewModel = hiltViewModel()
) {
    val componentName = viewModel.componentName
    val exampleName = viewModel.exampleName
    val component = Components.first { it.name == componentName }
    val example = component.examples.first { it.name == exampleName }

    MainAppScaffoldWithNavBar(
        title = componentName,
        onNavigationClick = { navController.popBackStack() },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            example.content()
        }
    }
}
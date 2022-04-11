package org.jdc.template.ux.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import org.jdc.template.ui.navigation.HandleNavBarNavigation
import org.jdc.template.util.ext.requireActivity
import org.jdc.template.ux.NavGraph

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(LocalContext.current.requireActivity()) // make sure we share the same ViewModel here and in TemplateAppScaffoldWithNavBar
) {
    val navController = rememberNavController()

    NavGraph(navController)

    HandleNavBarNavigation(viewModel, navController, viewModel.navigatorFlow)
}

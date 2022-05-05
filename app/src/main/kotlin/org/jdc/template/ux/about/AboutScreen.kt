package org.jdc.template.ux.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.jdc.template.BuildConfig
import org.jdc.template.R
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun AboutScreen(
    navController: NavController,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.OverflowMenuItem(stringResource(R.string.acknowledgments)) { uiState.licensesClicked() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.about),
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() }
    ) {
        AboutScreenContent(uiState)
    }

    HandleNavigation(viewModel, navController)
}

@Composable
private fun AboutScreenContent(
    uiState: AboutUiState
) {

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ApplicationAboutTitle()

        Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
        RestServicesStatus(uiState)
        TestButton("CREATE DATABASE") { uiState.createSampleData() }
        TestButton("TEST REST CALL") { uiState.testQueryWebServiceCall() }
        TestButton("TEST TABLE CHANGES") { uiState.testTableChange() }
        TestButton("TEST SIMPLE WORKMANAGER") { uiState.workManagerSimpleTest() }
        TestButton("TEST SYNC WORKMANAGER") { uiState.workManagerSyncTest() }
    }
}

@Composable
private fun ApplicationAboutTitle() {
    Column {
        Text(
            stringResource(R.string.about_title),
            style = MaterialTheme.typography.h4
        )
        Text(
            BuildConfig.VERSION_NAME,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun RestServicesStatus(uiState: AboutUiState) {
    val restServicesEnabled by uiState.resetServiceEnabledFlow.collectAsState()
    Text("Rest Services Enabled: $restServicesEnabled")
}

@Composable
private fun TestButton(text: String, block: () -> Unit) {
    Button(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        onClick = { block() }
    ) {
        Text(text)
    }
}
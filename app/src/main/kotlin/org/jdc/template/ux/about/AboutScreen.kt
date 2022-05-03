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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
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
    val appBarMenuItems = listOf(
        AppBarMenuItem.OverflowMenuItem(stringResource(R.string.acknowledgments)) { viewModel.licensesClicked() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.about),
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() }
    ) {
        AboutScreenContent(
            viewModel.resetServiceEnabledFlow,
            createSampleData = { viewModel.createSampleData() },
            testTableChange = { viewModel.testTableChange() },
            testQueryWebServiceCall = { viewModel.testQueryWebServiceCall() },
            workManagerSyncTest = { viewModel.workManagerSyncTest() },
            workManagerSimpleTest = { viewModel.workManagerSimpleTest() }
        )
    }

    HandleNavigation(viewModel, navController)
}

@Composable
private fun AboutScreenContent(
    resetServiceEnabledFlow: StateFlow<Boolean>,
    createSampleData: () -> Unit,
    testQueryWebServiceCall: () -> Unit,
    testTableChange: () -> Unit,
    workManagerSimpleTest: () -> Unit,
    workManagerSyncTest: () -> Unit,

) {

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ApplicationAboutTitle()

        Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
        RestServicesStatus(resetServiceEnabledFlow)
        TestButton("CREATE DATABASE") { createSampleData() }
        TestButton("TEST REST CALL") { testQueryWebServiceCall() }
        TestButton("TEST TABLE CHANGES") { testTableChange() }
        TestButton("TEST SIMPLE WORKMANAGER") { workManagerSimpleTest() }
        TestButton("TEST SYNC WORKMANAGER") { workManagerSyncTest() }
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
private fun RestServicesStatus(restServicesEnabledFlow: StateFlow<Boolean>) {
    val restServicesEnabled = restServicesEnabledFlow.collectAsState()
    Text("Rest Services Enabled: ${restServicesEnabled.value}")
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
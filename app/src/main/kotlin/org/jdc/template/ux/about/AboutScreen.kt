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
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.BuildConfig
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.navigation.HandleNavigation

@Composable
fun AboutScreen(viewModel: AboutViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val restServicesEnabledFlow = viewModel.resetServiceEnabledFlow

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ApplicationAboutTitle()

        Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
        RestServicesStatus(restServicesEnabledFlow)
        TestButton("CREATE DATABASE") { viewModel.createSampleData() }
        TestButton("TEST REST CALL") { viewModel.testQueryWebServiceCall() }
        TestButton("TEST TABLE CHANGES") { viewModel.testTableChange() }
        TestButton("TEST SIMPLE WORKMANAGER") { viewModel.workManagerSimpleTest() }
        TestButton("TEST SYNC WORKMANAGER") { viewModel.workManagerSyncTest() }
    }

    HandleNavigation(viewModel, navController, viewModel.navigateRouteFlow)
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
        onClick = { block() }) {
        Text(text)
    }
}
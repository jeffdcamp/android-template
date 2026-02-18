package org.jdc.template.ux.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.BuildConfig
import org.jdc.template.R
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.navigation.HandleNavigation3
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun AboutScreen(
    navigator: Navigation3Navigator,
    viewModel: AboutViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val appBarMenuItems = listOf(
        AppBarMenuItem.OverflowMenuItem(R.string.acknowledgments) { viewModel.onLicensesClick() }
    )

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.about),
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() }
    ) {
        when (val uiState = uiState) {
            AboutUiState.Loading -> {}
            is AboutUiState.Ready -> {
                AboutScreenContent(
                    uiState = uiState,
                    m3TypographyClick = { viewModel.testQueryWebServiceCall() },
                    createSampleData = { viewModel.createSampleData() },
                    createLargeSampleData = { viewModel.createLargeSampleData() },
                    testQueryWebServiceCall = { viewModel.testQueryWebServiceCall() },
                    testFullUrlQueryWebServiceCall = { viewModel.testFullUrlQueryWebServiceCall() },
                    testSaveQueryWebServiceCall = { viewModel.testSaveQueryWebServiceCall() },
                    testCachedUrlQueryWebServiceCall = { viewModel.testCachedUrlQueryWebServiceCall() },
                    onChatClick = { viewModel.onChatClick() },
                    workManagerSimpleTest = { viewModel.workManagerSimpleTest() },
                    workManagerSyncTest = { viewModel.workManagerSyncTest() }
                )

            }
        }
    }

    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun AboutScreenContent(
    uiState: AboutUiState.Ready,
    m3TypographyClick: () -> Unit,
    createSampleData: () -> Unit,
    createLargeSampleData: () -> Unit,
    testQueryWebServiceCall: () -> Unit,
    testFullUrlQueryWebServiceCall: () -> Unit,
    testSaveQueryWebServiceCall: () -> Unit,
    testCachedUrlQueryWebServiceCall: () -> Unit,
    onChatClick: () -> Unit,
    workManagerSimpleTest: () -> Unit,
    workManagerSyncTest: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        ApplicationAboutTitle()

        HorizontalDivider(Modifier.padding(top = 16.dp, bottom = 16.dp))
        RestServicesStatus(uiState.resetServiceEnabled)
        TestButton("M3 Typography") { m3TypographyClick() }
        TestButton("Create Database") { createSampleData() }
        TestButton("Create Large Database") { createLargeSampleData() }
        TestButton("Test Rest Call") { testQueryWebServiceCall() }
        TestButton("Test Rest Call2") { testFullUrlQueryWebServiceCall() }
        TestButton("Test Rest Call3") { testSaveQueryWebServiceCall() }
        TestButton("Test Rest Cached Call") { testCachedUrlQueryWebServiceCall() }
        TestButton("Chat Test") { onChatClick() }
        TestButton("TEST SIMPLE WORKMANAGER") { workManagerSimpleTest() }
        TestButton("TEST SYNC WORKMANAGER") { workManagerSyncTest() }
    }
}

@Composable
private fun ApplicationAboutTitle() {
    Column {
        Text(
            stringResource(R.string.about_title),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            BuildConfig.VERSION_NAME,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun RestServicesStatus(restServicesEnabled: Boolean) {
    Row {
        Text("Rest Services Enabled: ")
        Text(text = restServicesEnabled.toString(), color = AppTheme.extendedColors.customColorA.color)
    }
}

@Composable
private fun TestButton(text: String, block: () -> Unit) {
    Button(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        onClick = { block() }) {
        Text(text)
    }
}

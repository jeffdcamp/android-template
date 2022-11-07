package org.jdc.template.ux.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.jdc.template.R
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.compose.setting.Setting
import org.jdc.template.ui.navigation.WorkManagerStatusRoute
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun SettingsScreen(
    navController: NavController? = null,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val scrollState = rememberScrollState()

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.settings),
        hideNavigation = true,
        onNavigationClick = { navController?.popBackStack() },
    ) {
        Column(
            Modifier.verticalScroll(scrollState)
        ) {
            Setting.Header(stringResource(R.string.display))
            Setting.Clickable(stringResource(R.string.theme), uiState.currentThemeTitleFlow) { uiState.onThemeSettingClicked() }
            Setting.Switch(stringResource(R.string.sort_by_last_name), uiState.sortByLastNameFlow) { uiState.setSortByLastName(it) }

            // not translated because this should not be visible for release builds
            Setting.Header("Developer Options")
            Setting.Clickable(text = "Work Manager Status", secondaryText = "Show status of all background workers") {
                navController?.navigate(WorkManagerStatusRoute.createRoute())
            }
            Setting.Clickable(text = "Last Installed Version Code", uiState.currentLastInstalledVersionCodeFlow) { uiState.onLastInstalledVersionCodeClicked() }
            Setting.Clickable(text = "Range", uiState.rangeFlow) { uiState.onRangeClicked() }
        }

        HandleDialogUiState(uiState.dialogUiStateFlow)
    }
}

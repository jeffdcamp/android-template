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
import org.jdc.template.ui.compose.dialog.HandleDialog
import org.jdc.template.ui.compose.dialog.InputDialog
import org.jdc.template.ui.compose.dialog.RadioDialog
import org.jdc.template.ui.compose.setting.Setting
import org.jdc.template.ui.navigation.WorkManagerStatusRoute
import org.jdc.template.ux.TemplateAppScaffoldWithNavBar

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    TemplateAppScaffoldWithNavBar(
        title = stringResource(R.string.settings),
        onNavigationClick = { navController.popBackStack() }
    ) {
        Column(
            Modifier.verticalScroll(scrollState)
        ) {
            Setting.Header(stringResource(R.string.display))
            Setting.Clickable(stringResource(R.string.theme), viewModel.currentThemeTitleFlow) { viewModel.onThemeSettingClicked() }
            Setting.Switch(stringResource(R.string.sort_by_last_name), viewModel.sortByLastNameFlow) { viewModel.setSortByLastName(it) }

            // not translated because this should not be visible for release builds
            Setting.Header("Developer Options")
            Setting.Clickable(text = "Work Manager Status", secondaryText = "Show status of all background workers") {
                navController.navigate(WorkManagerStatusRoute.createRoute())
            }
            Setting.Clickable(text = "Last Installed Version Code", viewModel.currentLastInstalledVersionCodeFlow) { viewModel.onLastInstalledVersionCodeClicked() }
        }

        // Dialogs
        HandleDialog(viewModel.themeRadioDialogDataFlow) {
            RadioDialog(
                title = it.title,
                items = it.items,
                onItemSelected = { selectedItem -> viewModel.setTheme(selectedItem) },
                onDismissButtonClicked = { viewModel.dismissThemeDialog() }
            )
        }

        HandleDialog(viewModel.lastInstalledVersionCodeDialogDataFlow) {
            InputDialog(
                title = it.title,
                initialTextFieldText = it.initialTextFieldText,
                onConfirmButtonClicked = { text -> viewModel.setLastInstalledVersionCode(text) },
                onDismissButtonClicked = { viewModel.dismissSetLastInstalledVersionCodeDialog() },
                minLength = 1,
                maxLength = 20
            )
        }
    }
}

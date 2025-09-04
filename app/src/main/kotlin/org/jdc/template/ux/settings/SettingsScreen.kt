package org.jdc.template.ux.settings

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import org.jdc.template.R
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.compose.setting.Setting
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun SettingsScreen(
    navigator: Navigation3Navigator<NavKey>,
    viewModel: SettingsViewModel
) {
    val uiState = viewModel.uiState

    val scrollState = rememberScrollState()

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.settings),
        hideNavigation = true,
        onNavigationClick = { navigator.pop() },
    ) {
        Column(
            Modifier.verticalScroll(scrollState)
        ) {
            Setting.Header(stringResource(R.string.display))
            Setting.Clickable(stringResource(R.string.theme), uiState.currentThemeTitleFlow) { uiState.onThemeSettingClick() }
            // Dynamic themes are only available on Android 13+
            if (Build.VERSION.SDK_INT >= 33) {
                Setting.Switch(stringResource(R.string.dynamic_theme), uiState.dynamicThemeFlow) { uiState.setDynamicTheme(it) }
            }
            Setting.Switch(stringResource(R.string.sort_by_last_name), uiState.sortByLastNameFlow) { uiState.setSortByLastName(it) }

            // not translated because this should not be visible for release builds
            Setting.Header("Developer Options")
            Setting.Clickable(text = "Last Installed Version Code", uiState.currentLastInstalledVersionCodeFlow) { uiState.onLastInstalledVersionCodeClick() }
            Setting.Clickable(text = "Range", uiState.rangeFlow) { uiState.onRangeClick() }
        }

        HandleDialogUiState(uiState.dialogUiStateFlow)
    }
}

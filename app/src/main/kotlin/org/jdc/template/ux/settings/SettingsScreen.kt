package org.jdc.template.ux.settings

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.R
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.compose.setting.Setting
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun SettingsScreen(
    navigator: Navigation3Navigator,
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.settings),
        hideNavigation = true,
        onNavigationClick = { navigator.pop() },
    ) {
        when (val uiState = uiState) {
            SettingsUiState.Loading -> {}
            is SettingsUiState.Ready -> {
                SettingsContent(
                    uiState = uiState,
                    onThemeSettingClick = { viewModel.onThemeSettingClick() },
                    onLastInstalledVersionCodeClick = { viewModel.onLastInstalledVersionCodeClick() },
                    setSortByLastName = { viewModel.setSortByLastName(it) },
                    setDynamicTheme = { viewModel.setDynamicTheme(it) },
                    onRangeClick = { viewModel.onRangeClick() }
                )
            }
        }
    }

    HandleDialogUiState(viewModel.dialogUiStateFlow)
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState.Ready,
    onThemeSettingClick: () -> Unit,
    onLastInstalledVersionCodeClick: () -> Unit,
    setSortByLastName: (Boolean) -> Unit,
    setDynamicTheme: (Boolean) -> Unit,
    onRangeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier.verticalScroll(scrollState)
    ) {
        Setting.Group(headerText = stringResource(R.string.display)) {
            GroupedClickable(position = Setting.GroupPositionType.FIRST, text = stringResource(R.string.theme), secondaryText = uiState.currentThemeTitle) { onThemeSettingClick() }
            // Dynamic themes are only available on Android 13+
            if (Build.VERSION.SDK_INT >= 33) {
                GroupedSwitch(position = Setting.GroupPositionType.MIDDLE, text = stringResource(R.string.dynamic_theme), selected = uiState.dynamicTheme) { setDynamicTheme(it) }
            }
            GroupedSwitch(position = Setting.GroupPositionType.LAST, text = stringResource(R.string.sort_by_last_name), selected = uiState.sortByLastName) { setSortByLastName(it) }
        }

        // not translated because this should not be visible for release builds
        Setting.Group(headerText = "Developer Options") {
            GroupedClickable(
                position = Setting.GroupPositionType.FIRST,
                text = "Last Installed Version Code",
                secondaryText = uiState.currentLastInstalledVersionCode
            ) { onLastInstalledVersionCodeClick() }

            GroupedClickable(position = Setting.GroupPositionType.LAST, text = "Range", secondaryText = uiState.range) { onRangeClick() }
        }
    }
}

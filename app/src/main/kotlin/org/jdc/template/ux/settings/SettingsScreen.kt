@file:OptIn(ExperimentalMaterialApi::class)

package org.jdc.template.ux.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.RadioDialog
import org.jdc.template.ui.compose.collectAsLifecycleState
import org.jdc.template.ui.compose.dialog.InputDialog

@Composable
fun SettingsScreen() {
    val navController = LocalNavController.current
    val viewModel: SettingsViewModel = viewModel()

    // subtitles
    val currentThemeTitleFlow = viewModel.currentThemeTitleFlow
    val currentLastInstalledVersionCodeFlow = viewModel.currentLastInstalledVersionCodeFlow

    // switch values
    val sortByLastName by viewModel.sortByLastNameFlow.collectAsLifecycleState(false)

    // dialog data
    val themeRadioDialogData by viewModel.themeRadioDialogData.collectAsState()
    val lastInstalledVersionCodeDialogData by viewModel.lastInstalledVersionCodeDialogData.collectAsState()

    Column {
        SettingsHeader(stringResource(R.string.display))
        ClickableSetting(stringResource(R.string.theme), currentThemeTitleFlow) { viewModel.onThemeSettingClicked() }
        SwitchSetting(stringResource(R.string.sort_by_last_name), checked = sortByLastName) { checked ->
            viewModel.setSortByLastName(checked)
        }

        // not translated because this should not be visible for release builds
        SettingsHeader("Developer Options")
        ClickableSetting(text = "Work Manager Status", secondaryText = "Show status of all background workers") {
            navController?.navigate(SettingsFragmentDirections.actionToWorkManagerFragment())
        }
        ClickableSetting(text = "Last Installed Version Code", currentLastInstalledVersionCodeFlow) {
            viewModel.onLastInstalledVersionCodeClicked()
        }
    }

    // Dialogs
    if (themeRadioDialogData.visible) {
        RadioDialog(
            title = themeRadioDialogData.title,
            items = themeRadioDialogData.items,
            onItemSelected = { viewModel.setTheme(it) },
            onDismissButtonClicked = { viewModel.dismissThemeDialog() }
        )
    }

    if (lastInstalledVersionCodeDialogData.visible) {
        InputDialog(
            title = lastInstalledVersionCodeDialogData.title,
            initialTextFieldText = lastInstalledVersionCodeDialogData.initialTextFieldText,
            onConfirmButtonClicked = { text -> viewModel.setLastInstalledVersionCode(text) },
            onDismissButtonClicked = { viewModel.dismissSetLastInstalledVersionCodeDialog() }
        )
    }
}

@Composable
private fun SettingsHeader(text: String) {
    Text(
        text,
        modifier = Modifier.padding(start = 72.dp, top = 16.dp, bottom = 4.dp), // start is the icon (blank) with padding (16 + 40) + setting text padding (16)
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.secondary
    )
}

@Composable
private fun SwitchSetting(
    text: String,
    checked: Boolean = false,
    secondaryText: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClickBody: ((Boolean) -> Unit)? = null
) {
    ListItem(
        icon = { icon?.invoke() },
        text = {
            Text(text)
        },
        secondaryText = if (secondaryText != null) {
            { Text(secondaryText) }
        } else {
            null
        },
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onClickBody
            )
        }
    )
}

@Composable
private fun ClickableSetting(
    text: String,
    secondaryText: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClickBody: (() -> Unit)? = null
) {
    ListItem(
        modifier = Modifier
            .clickable { onClickBody?.invoke() },
        icon = { icon?.invoke() },
        text = {
            Text(text)
        },
        secondaryText = if (secondaryText != null) {
            { Text(secondaryText) }
        } else {
            null
        }
    )
}

@Composable
private fun ClickableSetting(
    text: String,
    currentValueFlow: Flow<String>,
    icon: @Composable (() -> Unit)? = null,
    onClickBody: (() -> Unit)? = null
) {
    val currentValue by currentValueFlow.collectAsLifecycleState("")

    ClickableSetting(
        text = text,
        secondaryText = currentValue,
        icon = icon,
        onClickBody = onClickBody
    )
}


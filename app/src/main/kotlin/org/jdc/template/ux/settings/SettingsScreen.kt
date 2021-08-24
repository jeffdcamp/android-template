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

@Composable
fun SettingsScreen() {
    val navController = LocalNavController.current
    val viewModel: SettingsViewModel = viewModel()
    val currentThemeTitleFlow = viewModel.currentThemeTitleFlow
    val sortByLastName by viewModel.sortByLastNameFlow.collectAsLifecycleState(false)

    Column {
        SettingsHeader(stringResource(R.string.display))
        ThemeSetting(currentThemeTitleFlow) {
            viewModel.onThemeSettingClicked()
        }
        SwitchSetting(stringResource(R.string.sort_by_last_name), checked = sortByLastName) { checked ->
            viewModel.setSortByLastName(checked)
        }

        // not translated because this should not be visible for release builds
        SettingsHeader("Developer Options")
        BasicClickableSetting(text = "Work Manager Status", secondaryText = "Show status of all background workers") {
            navController?.navigate(SettingsFragmentDirections.actionToWorkManagerFragment())
        }
    }

    val radioDialogData by viewModel.radioDialogData.collectAsState()
    RadioDialog(
        visible = radioDialogData.visible,
        title = radioDialogData.title,
        items = radioDialogData.items,
        onItemSelected = { selectedTheme ->
            viewModel.setTheme(selectedTheme)
        },
        onDismissButtonClicked = {
            viewModel.dismissThemeDialog()
        }
    )
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
private fun ThemeSetting(currentThemeTitleFlow: Flow<String>, onClickBody: (() -> Unit)) {
    val currentThemeTitle by currentThemeTitleFlow.collectAsLifecycleState("")

    ListItem(
        modifier = Modifier
            .clickable { onClickBody() },
        icon = {},
        text = {
            Text(stringResource(R.string.theme))
        },
        secondaryText = {
            Text(currentThemeTitle)
        }
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
private fun BasicClickableSetting(
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



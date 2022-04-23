package org.jdc.template.ui.compose.setting

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.theme.AppTheme

object Setting {
    @Composable
    fun Header(text: String) {
        Text(
            text,
            modifier = Modifier.padding(start = 72.dp, top = 16.dp, bottom = 4.dp), // start is the icon (blank) with padding (16 + 40) + setting text padding (16)
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondary
        )
    }

    @Composable
    fun Switch(
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
    fun Switch(
        text: String,
        currentCheckedValueFlow: StateFlow<Boolean>,
        secondaryText: String? = null,
        icon: @Composable (() -> Unit)? = null,
        onClickBody: ((Boolean) -> Unit)? = null
    ) {
        val currentValueChecked by currentCheckedValueFlow.collectAsState()

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
                    checked = currentValueChecked,
                    onCheckedChange = onClickBody
                )
            }
        )
    }

    @Composable
    fun Clickable(
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
    fun Clickable(
        text: String,
        currentValueFlow: StateFlow<String?>,
        icon: @Composable (() -> Unit)? = null,
        onClickBody: (() -> Unit)? = null
    ) {
        val currentValue by currentValueFlow.collectAsState()

        Clickable(
            text = text,
            secondaryText = currentValue,
            icon = icon,
            onClickBody = onClickBody
        )
    }
}

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun SettingsPreview() {
    val currentThemeTitleFlow = MutableStateFlow("Light Theme")
    val sortByLastNameFlow = MutableStateFlow(true)
    val currentLastInstalledVersionCodeFlow = MutableStateFlow("1234")

    AppTheme {
        Scaffold {
            val scrollState = rememberScrollState()

            Column(
                Modifier.verticalScroll(scrollState)
            ) {
                Setting.Header("Display")
                Setting.Clickable("Theme", currentThemeTitleFlow) { }
                Setting.Switch("Sort by last name", sortByLastNameFlow) { }

                // not translated because this should not be visible for release builds
                Setting.Header("Developer Options")
                Setting.Clickable(text = "Work Manager Status", secondaryText = "Show status of all background workers") { }
                Setting.Clickable(text = "Last Installed Version Code", currentLastInstalledVersionCodeFlow) { }
            }
        }
    }
}

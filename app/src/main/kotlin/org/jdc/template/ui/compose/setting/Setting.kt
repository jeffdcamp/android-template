package org.jdc.template.ui.compose.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

object Setting {
    @Composable
    fun Header(text: String) {
        Text(
            text,
            modifier = Modifier.padding(start = 72.dp, top = 16.dp, bottom = 4.dp), // start is the icon (blank) with padding (16 + 40) + setting text padding (16)
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary
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
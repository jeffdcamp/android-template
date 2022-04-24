package org.jdc.template.ui.compose.setting

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.theme.AppTheme
import kotlin.math.roundToInt

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
    @Composable
    fun Slider(
        text: String,
        valueFlow: StateFlow<Float>,
        range: ClosedFloatingPointRange<Float> = 0f..1f,
        steps: Int = 0,
        icon: @Composable (() -> Unit)? = null,
        onValueChangeFinished: ((Float) -> Unit),
    ) {
        val value by valueFlow.collectAsState()

        var sliderPosition: Float by remember(value) { mutableStateOf(value) }

        Row {
            if (icon != null) {
                Box(
                    Modifier
                        .sizeIn(
                            minWidth = 64.dp,
                            minHeight = 64.dp
                        )
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        ),
                    contentAlignment = Alignment.TopStart
                ) { icon() }
            } else {
                Box(
                    Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        ),
                )
            }

            Column(
                Modifier
                    .weight(2f)
            ) {
                Text(
                    text = text,
                    Modifier
                        .padding(start = 8.dp, end = 16.dp, top = 8.dp)
                )
                Row {
                    androidx.compose.material3.Slider(
                        value = sliderPosition,
                        onValueChange = { newValue -> sliderPosition = newValue.round(1) },
                        valueRange = range,
                        steps = steps,
                        onValueChangeFinished = { onValueChangeFinished(sliderPosition) },
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "${value}x",
                        Modifier
                            .padding(start = 8.dp, end = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

private fun Float.round(decimalPlaces: Int): Float {
    val multiplier = 10 * decimalPlaces
    return (multiplier * this).roundToInt().toFloat() / multiplier
}

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun SettingsPreview() {
    val currentThemeTitleFlow = MutableStateFlow("Light Theme")
    val sortByLastNameFlow = MutableStateFlow(true)
    val currentLastInstalledVersionCodeFlow = MutableStateFlow("1234")
    val playbackSpeedFlow = MutableStateFlow(1.0f)

    AppTheme {
        Scaffold {
            val scrollState = rememberScrollState()

            Column(
                Modifier.verticalScroll(scrollState)
            ) {
                Setting.Header("Display")
                Setting.Clickable("Theme", currentThemeTitleFlow) { }
                Setting.Switch("Sort by last name", sortByLastNameFlow) { }
                Setting.Slider("Playback Speed", playbackSpeedFlow, range = .5f..3f) { value ->  }

                // not translated because this should not be visible for release builds
                Setting.Header("Developer Options")
                Setting.Clickable(text = "Work Manager Status", secondaryText = "Show status of all background workers") { }
                Setting.Clickable(text = "Last Installed Version Code", currentLastInstalledVersionCodeFlow) { }
            }
        }
    }
}

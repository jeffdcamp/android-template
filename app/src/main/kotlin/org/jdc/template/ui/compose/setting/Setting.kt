package org.jdc.template.ui.compose.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme
import kotlin.math.pow
import kotlin.math.roundToInt

object Setting {
    enum class GroupPositionType {
        ONLY,
        FIRST,
        MIDDLE,
        LAST
    }

    @Composable
    fun Group(
        modifier: Modifier = Modifier,
        headerText: String? = null,
        content: @Composable SegmentedScope.() -> Unit
    ) {
        val colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        val topPadding = if (headerText == null) 16.dp else 24.dp

        Column(
            modifier = modifier.fillMaxWidth().padding(start = 16.dp, top = topPadding, end = 16.dp)
        ) {
            if (headerText != null) {
                Text(
                    text = headerText,
                    modifier = Modifier.padding(vertical = 8.dp).semantics { heading() },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
            ) {
                SegmentedScope(colors = colors).content()
            }
        }
    }

    class SegmentedScope internal constructor(
        private val colors: ListItemColors,
        // Keeps expanded rows full width while indenting only their internal content.
        private val inheritedContentStartPadding: Dp = 0.dp
    ) {
        @Composable
        fun GroupedClickable(
            position: GroupPositionType,
            text: String,
            currentValueFlow: StateFlow<String?>,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            icon: @Composable (() -> Unit)? = null,
            onClickBody: (() -> Unit)? = null
        ) {
            val currentValue by currentValueFlow.collectAsStateWithLifecycle()

            GroupedClickable(
                position = position,
                text = text,
                enabled = enabled,
                modifier = modifier,
                secondaryText = currentValue,
                icon = icon,
                onClickBody = onClickBody
            )
        }

        @Composable
        fun GroupedClickable(
            position: GroupPositionType,
            text: String,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            secondaryText: String? = null,
            icon: @Composable (() -> Unit)? = null,
            onClickBody: (() -> Unit)? = null
        ) {
            if (onClickBody != null) {
                SegmentedListItem(
                    onClick = onClickBody,
                    shapes = nextShapes(position),
                    modifier = modifier,
                    enabled = enabled,
                    leadingContent = indentedLeadingContent(icon),
                    supportingContent = secondaryText?.takeUnless { it.isBlank() }?.let {
                        { SupportingText(text = it, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                    },
                    colors = colors,
                    content = { Text(text = text, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                )
            } else {
                SegmentedListItem(
                    shapes = nextShapes(position),
                    modifier = modifier,
                    enabled = enabled,
                    leadingContent = indentedLeadingContent(icon),
                    supportingContent = secondaryText?.takeUnless { it.isBlank() }?.let {
                        { SupportingText(text = it, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                    },
                    colors = colors,
                    content = { Text(text = text, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                )
            }
        }

        @Composable
        fun GroupedSwitch(
            position: GroupPositionType,
            text: String,
            currentCheckedValueFlow: StateFlow<Boolean>,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            secondaryText: String? = null,
            icon: @Composable (() -> Unit)? = null,
            onClickBody: ((Boolean) -> Unit)? = null
        ) {
            val currentValueChecked by currentCheckedValueFlow.collectAsStateWithLifecycle()

            GroupedSwitch(
                position = position,
                text = text,
                enabled = enabled,
                modifier = modifier,
                selected = currentValueChecked,
                secondaryText = secondaryText,
                icon = icon,
                onClickBody = onClickBody
            )
        }

        @Composable
        fun GroupedSwitch(
            position: GroupPositionType,
            text: String,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            selected: Boolean = false,
            secondaryText: String? = null,
            icon: @Composable (() -> Unit)? = null,
            onClickBody: ((Boolean) -> Unit)? = null
        ) {
            val itemModifier = modifier.semantics {
                if (onClickBody != null) {
                    role = Role.Switch
                    toggleableState = ToggleableState(selected)
                }
            }

            if (onClickBody != null) {
                SegmentedListItem(
                    onClick = { onClickBody(!selected) },
                    shapes = nextShapes(position),
                    modifier = itemModifier,
                    enabled = enabled,
                    leadingContent = indentedLeadingContent(icon),
                    trailingContent = { Switch(checked = selected, onCheckedChange = null, enabled = enabled) },
                    supportingContent = secondaryText?.takeUnless { it.isBlank() }?.let {
                        { SupportingText(text = it, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                    },
                    colors = colors,
                    content = { Text(text = text, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                )
            } else {
                SegmentedListItem(
                    shapes = nextShapes(position),
                    modifier = itemModifier,
                    enabled = enabled,
                    leadingContent = indentedLeadingContent(icon),
                    trailingContent = { Switch(checked = selected, onCheckedChange = null, enabled = enabled) },
                    supportingContent = secondaryText?.takeUnless { it.isBlank() }?.let {
                        { SupportingText(text = it, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                    },
                    colors = colors,
                    content = { Text(text = text, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                )
            }
        }

        @Composable
        fun GroupedSlider(
            position: GroupPositionType,
            text: String,
            valueFlow: StateFlow<Float>,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            range: ClosedFloatingPointRange<Float> = 0f..1f,
            steps: Int = 0,
            icon: @Composable (() -> Unit)? = null,
            valueText: @Composable (Float) -> String = { "$it" },
            onValueChangeFinished: (Float) -> Unit
        ) {
            val value by valueFlow.collectAsStateWithLifecycle()

            GroupedSlider(
                position = position,
                text = text,
                value = value,
                enabled = enabled,
                modifier = modifier,
                range = range,
                steps = steps,
                icon = icon,
                valueText = valueText,
                onValueChangeFinished = onValueChangeFinished
            )
        }

        @Composable
        fun GroupedSlider(
            position: GroupPositionType,
            text: String,
            value: Float,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            range: ClosedFloatingPointRange<Float> = 0f..1f,
            steps: Int = 0,
            icon: @Composable (() -> Unit)? = null,
            valueText: @Composable (Float) -> String = { "$it" },
            onValueChangeFinished: (Float) -> Unit
        ) {
            var sliderPosition: Float by remember(value) { mutableFloatStateOf(value) }

            val formattedValueText = valueText(sliderPosition)

            SegmentedListItem(
                shapes = nextShapes(position),
                enabled = enabled,
                modifier = modifier.clearAndSetSemantics {
                    contentDescription = text
                    stateDescription = formattedValueText
                    progressBarRangeInfo = ProgressBarRangeInfo(
                        current = sliderPosition,
                        range = range,
                        steps = steps
                    )
                    setProgress { targetValue ->
                        if (!enabled) return@setProgress false

                        val adjustedValue = targetValue.coerceIn(range).round(1)
                        if (sliderPosition == adjustedValue) {
                            false
                        } else {
                            sliderPosition = adjustedValue
                            onValueChangeFinished(sliderPosition)
                            true
                        }
                    }
                },
                leadingContent = icon,
                supportingContent = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Slider(
                            value = sliderPosition,
                            onValueChange = { newValue -> sliderPosition = newValue.round(1) },
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .clearAndSetSemantics {},
                            enabled = enabled,
                            valueRange = range,
                            steps = steps,
                            onValueChangeFinished = { onValueChangeFinished(sliderPosition) }
                        )
                        Text(text = formattedValueText, modifier = Modifier.align(Alignment.CenterVertically).clearAndSetSemantics {})
                    }
                },
                colors = colors,
                content = { Text(text = text, modifier = Modifier.clearAndSetSemantics {}) }
            )
        }

        @Composable
        fun GroupedClickableWithExpansion(
            position: GroupPositionType,
            text: String,
            currentValueFlow: StateFlow<String?>,
            expanded: Boolean,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            icon: @Composable (() -> Unit)? = null,
            expandedContentIndent: Dp = 16.dp,
            onClickBody: (() -> Unit)? = null,
            expandedContent: @Composable SegmentedScope.() -> Unit
        ) {
            val currentValue by currentValueFlow.collectAsStateWithLifecycle()

            GroupedClickableWithExpansion(
                position = position,
                text = text,
                expanded = expanded,
                enabled = enabled,
                modifier = modifier,
                secondaryText = currentValue,
                icon = icon,
                expandedContentIndent = expandedContentIndent,
                onClickBody = onClickBody,
                expandedContent = expandedContent
            )
        }

        @Composable
        fun GroupedClickableWithExpansion(
            position: GroupPositionType,
            text: String,
            expanded: Boolean,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            secondaryText: String? = null,
            icon: @Composable (() -> Unit)? = null,
            expandedContentIndent: Dp = 16.dp,
            onClickBody: (() -> Unit)? = null,
            expandedContent: @Composable SegmentedScope.() -> Unit
        ) {
            GroupedClickable(
                position = position,
                text = text,
                enabled = enabled,
                secondaryText = secondaryText,
                modifier = modifier,
                icon = icon,
                onClickBody = onClickBody
            )
            ExpansionContent(expanded = expanded, expandedContentIndent = expandedContentIndent, expandedContent = expandedContent)
        }

        @Composable
        fun GroupedSwitchWithExpansion(
            position: GroupPositionType,
            text: String,
            currentCheckedValueFlow: StateFlow<Boolean>,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            secondaryText: String? = null,
            icon: @Composable (() -> Unit)? = null,
            expandedContentIndent: Dp = 16.dp,
            onClickBody: ((Boolean) -> Unit)? = null,
            expandedContent: @Composable SegmentedScope.() -> Unit
        ) {
            val currentValueChecked by currentCheckedValueFlow.collectAsStateWithLifecycle()

            GroupedSwitchWithExpansion(
                position = position,
                text = text,
                selected = currentValueChecked,
                enabled = enabled,
                modifier = modifier,
                secondaryText = secondaryText,
                icon = icon,
                expandedContentIndent = expandedContentIndent,
                onClickBody = onClickBody,
                expandedContent = expandedContent
            )
        }

        @Composable
        fun GroupedSwitchWithExpansion(
            position: GroupPositionType,
            text: String,
            selected: Boolean,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            secondaryText: String? = null,
            icon: @Composable (() -> Unit)? = null,
            expandedContentIndent: Dp = 16.dp,
            onClickBody: ((Boolean) -> Unit)? = null,
            expandedContent: @Composable SegmentedScope.() -> Unit
        ) {
            GroupedSwitch(
                position = position,
                text = text,
                enabled = enabled,
                modifier = modifier,
                selected = selected,
                secondaryText = secondaryText,
                icon = icon,
                onClickBody = onClickBody
            )
            ExpansionContent(expanded = selected, expandedContentIndent = expandedContentIndent, expandedContent = expandedContent)
        }

        @Composable
        fun GroupedSwitchWithAction(
            position: GroupPositionType,
            text: String,
            currentEnabledFlow: StateFlow<Boolean>,
            currentValueFlow: StateFlow<String?>,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            onClickBody: (() -> Unit)? = null,
            onToggle: ((Boolean) -> Unit)? = null
        ) {
            val currentEnabledValue by currentEnabledFlow.collectAsStateWithLifecycle()
            val currentValue by currentValueFlow.collectAsStateWithLifecycle()

            GroupedSwitchWithAction(
                position = position,
                text = text,
                enabled = enabled,
                modifier = modifier,
                checked = currentEnabledValue,
                currentValue = currentValue.orEmpty(),
                onClickBody = onClickBody,
                onToggle = onToggle
            )
        }

        @Composable
        fun GroupedSwitchWithAction(
            position: GroupPositionType,
            text: String,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            checked: Boolean = false,
            currentValue: String,
            onClickBody: (() -> Unit)? = null,
            onToggle: ((Boolean) -> Unit)? = null
        ) {
            val itemModifier = if (onClickBody != null) {
                modifier.clickable(enabled = enabled, onClick = onClickBody)
            } else {
                modifier
            }

            SegmentedListItem(
                shapes = nextShapes(position),
                enabled = enabled,
                modifier = itemModifier.semantics(mergeDescendants = true) {},
                trailingContent = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VerticalDivider(modifier = Modifier.height(40.dp).padding(end = 4.dp), color = MaterialTheme.colorScheme.outline)
                        Box(
                            modifier = Modifier.minimumInteractiveComponentSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Switch(
                                checked = checked,
                                onCheckedChange = onToggle,
                                enabled = enabled,
                                modifier = Modifier.semantics {
                                    contentDescription = text
                                }
                            )
                        }
                    }
                },
                supportingContent = currentValue.takeUnless { it.isBlank() }?.let {
                    { SupportingText(text = it, modifier = Modifier.itemContentStartModifier()) }
                },
                colors = colors,
                content = { Text(text = text, modifier = Modifier.itemContentStartModifier()) }
            )
        }

        @Composable
        fun GroupedSwitchWithTwoButtons(
            position: GroupPositionType,
            text: String,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            checked: Boolean = false,
            icon: @Composable (() -> Unit)? = null,
            secondaryText: String? = null,
            buttonText: String? = null,
            secondaryButtonText: String? = null,
            onClickBody: ((Boolean) -> Unit)? = null,
            onClickButton: (() -> Unit)? = null,
            onClickSecondaryButton: (() -> Unit)? = null
        ) {
            val hasButtonContent = !buttonText.isNullOrBlank() || !secondaryButtonText.isNullOrBlank()
            val hasSupportingContent = !secondaryText.isNullOrBlank() || hasButtonContent

            val supportingContent = if (hasSupportingContent) {
                @Composable {
                    Column(modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) {
                        secondaryText?.takeUnless { it.isBlank() }?.let {
                            SupportingText(text = it)
                        }

                        AnimatedVisibility(
                            visible = checked && hasButtonContent,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                buttonText?.takeUnless { it.isBlank() }?.let {
                                    FilledTonalButton(
                                        onClick = { onClickButton?.invoke() },
                                        enabled = enabled && onClickButton != null,
                                        contentPadding = ButtonDefaults.contentPaddingFor(buttonHeight = ButtonDefaults.MinHeight, hasStartIcon = false, hasEndIcon = false)
                                    ) {
                                        Text(text = it)
                                    }
                                }

                                secondaryButtonText?.takeUnless { it.isBlank() }?.let {
                                    FilledTonalButton(
                                        onClick = { onClickSecondaryButton?.invoke() },
                                        enabled = enabled && onClickSecondaryButton != null,
                                        contentPadding = ButtonDefaults.contentPaddingFor(buttonHeight = ButtonDefaults.MinHeight, hasStartIcon = false, hasEndIcon = false)
                                    ) {
                                        Text(text = it)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                null
            }

            if (onClickBody != null) {
                SegmentedListItem(
                    onClick = { onClickBody(!checked) },
                    shapes = nextShapes(position),
                    modifier = modifier,
                    enabled = enabled,
                    leadingContent = indentedLeadingContent(icon),
                    trailingContent = { Switch(checked = checked, onCheckedChange = null, enabled = enabled) },
                    supportingContent = supportingContent,
                    colors = colors,
                    content = { Text(text = text, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                )
            } else {
                SegmentedListItem(
                    shapes = nextShapes(position),
                    modifier = modifier,
                    enabled = enabled,
                    leadingContent = indentedLeadingContent(icon),
                    trailingContent = { Switch(checked = checked, onCheckedChange = null, enabled = enabled) },
                    supportingContent = supportingContent,
                    colors = colors,
                    content = { Text(text = text, modifier = Modifier.itemContentStartModifier(hasLeadingContent = icon != null)) }
                )
            }
        }

        @Composable
        private fun ExpansionContent(
            expanded: Boolean,
            expandedContentIndent: Dp,
            expandedContent: @Composable SegmentedScope.() -> Unit
        ) {
            if (expanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    SegmentedScope(
                        colors = colors,
                        inheritedContentStartPadding = inheritedContentStartPadding + expandedContentIndent
                    ).expandedContent()
                }
            }
        }

        @Composable
        private fun nextShapes(position: GroupPositionType): ListItemShapes {
            val defaultShapes = if (position == GroupPositionType.ONLY) {
                val shape = MaterialTheme.shapes.large
                ListItemDefaults.shapes(
                    shape = shape,
                    selectedShape = shape,
                    pressedShape = shape,
                    focusedShape = shape,
                    hoveredShape = shape,
                    draggedShape = shape
                )
            } else {
                ListItemDefaults.shapes()
            }

            val (index, count) = when (position) {
                GroupPositionType.ONLY -> 0 to 1
                GroupPositionType.FIRST -> 0 to 3
                GroupPositionType.MIDDLE -> 1 to 3
                GroupPositionType.LAST -> 2 to 3
            }

            return ListItemDefaults.segmentedShapes(index = index, count = count, defaultShapes = defaultShapes)
        }

        private fun indentedLeadingContent(content: @Composable (() -> Unit)?): @Composable (() -> Unit)? {
            return content?.let {
                {
                    Box(modifier = Modifier.padding(start = inheritedContentStartPadding)) {
                        it()
                    }
                }
            }
        }

        private fun Modifier.itemContentStartModifier(hasLeadingContent: Boolean = false): Modifier {
            return if (hasLeadingContent) {
                this
            } else {
                padding(start = inheritedContentStartPadding)
            }
        }
    }
}

@Composable
private fun SupportingText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier)
}

private fun Float.round(decimalPlaces: Int): Float {
    val multiplier = 10f.pow(decimalPlaces)
    return (multiplier * this).roundToInt().toFloat() / multiplier
}

@PreviewDefault
@Composable
private fun SettingsGroupPreview() {
    val currentThemeTitleFlow = MutableStateFlow("Light Theme")
    val sortByLastNameFlow = MutableStateFlow(true)

    AppTheme {
        Surface {
            Setting.Group(headerText = "Segmented Settings") {
                GroupedClickable(position = Setting.GroupPositionType.FIRST, text = "Theme", currentValueFlow = currentThemeTitleFlow) { }

                GroupedSwitch(
                    position = Setting.GroupPositionType.MIDDLE,
                    text = "Sort by last name",
                    currentCheckedValueFlow = sortByLastNameFlow,
                    icon = { Icon(imageVector = Icons.AutoMirrored.Default.List, contentDescription = null) }
                ) { }

                GroupedClickable(
                    position = Setting.GroupPositionType.LAST,
                    text = "Display Settings",
                    currentValueFlow = currentThemeTitleFlow
                )
            }
        }
    }
}

@PreviewDefault
@Composable
private fun SettingsGroupActionsPreview() {
    val customDateTextFlow = MutableStateFlow("Jan 1, 2026")
    val customDateEnabledFlow = MutableStateFlow(true)

    AppTheme {
        Surface {
            Setting.Group(headerText = "Grouped Actions") {
                GroupedSwitchWithAction(
                    position = Setting.GroupPositionType.FIRST,
                    text = "Enable Custom Date",
                    currentEnabledFlow = customDateEnabledFlow,
                    currentValueFlow = customDateTextFlow,
                    onClickBody = { },
                    onToggle = { }
                )

                GroupedSwitchWithTwoButtons(
                    position = Setting.GroupPositionType.MIDDLE,
                    text = "Elders Quorum Lessons",
                    checked = true,
                    secondaryText = "Scheduled by you",
                    buttonText = "9:00 AM",
                    secondaryButtonText = "Mon, Tue, Wed, Thu, Fri, Sat",
                    onClickBody = { _ -> },
                    onClickButton = { },
                    onClickSecondaryButton = { }
                )

                GroupedSlider(
                    position = Setting.GroupPositionType.LAST,
                    text = "Unit Search Radius",
                    value = 1.3f,
                    range = .5f..3f
                ) { }
            }
        }
    }
}

@PreviewDefault
@Composable
private fun SettingsGroupExpansionPreview() {
    val proxyEnabledFlow = MutableStateFlow(true)
    val proxyUseCmisIdFlow = MutableStateFlow(false)
    val proxyUnitFlow = MutableStateFlow("Test Ward")
    val proxyCallingFlow = MutableStateFlow("Bishop")

    AppTheme {
        Surface {
            Setting.Group(headerText = "Expansion") {
                GroupedSwitchWithExpansion(
                    position = Setting.GroupPositionType.FIRST,
                    text = "Enable Proxy",
                    currentCheckedValueFlow = proxyEnabledFlow,
                    secondaryText = "Requires Unit Program Environment: STAGE",
                    onClickBody = { _ -> }
                ) {
                    GroupedSwitch(
                        position = Setting.GroupPositionType.MIDDLE,
                        text = "Use CMIS_ID for Proxy",
                        currentCheckedValueFlow = proxyUseCmisIdFlow
                    ) { }

                    GroupedClickable(position = Setting.GroupPositionType.MIDDLE, text = "Proxy Unit", currentValueFlow = proxyUnitFlow) { }
                    GroupedClickable(position = Setting.GroupPositionType.LAST, text = "Proxy Calling", currentValueFlow = proxyCallingFlow) { }
                }
            }
        }
    }
}

@PreviewDefault
@Composable
private fun SettingsGroupExpansionCompactPreview() {
    val proxyEnabledFlow = MutableStateFlow(true)
    val proxyUseCmisIdFlow = MutableStateFlow(true)

    AppTheme {
        Surface {
            Setting.Group(headerText = "Expansion") {
                GroupedSwitchWithExpansion(
                    position = Setting.GroupPositionType.FIRST,
                    text = "Enable Proxy",
                    currentCheckedValueFlow = proxyEnabledFlow,
                    secondaryText = "Requires Unit Program Environment: STAGE",
                    onClickBody = { _ -> }
                ) {
                    GroupedSwitch(
                        position = Setting.GroupPositionType.LAST,
                        text = "Use CMIS_ID for Proxy",
                        currentCheckedValueFlow = proxyUseCmisIdFlow
                    ) { }
                }
            }
        }
    }
}

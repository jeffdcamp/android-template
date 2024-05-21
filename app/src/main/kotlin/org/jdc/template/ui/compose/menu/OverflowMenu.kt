package org.jdc.template.ui.compose.menu

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jdc.template.R

@Composable
fun OverflowMenu(
    menuItems: List<OverflowMenuItem>,
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector = Icons.Default.MoreVert,
    iconTintColor: Color = LocalContentColor.current,
    showIcon: Boolean = true,
    touchRadius: Dp = 20.dp, // 20.dp is the same size as a default IconButton touch area
    contentDescription: String? = stringResource(R.string.more_options),
) {
    if (menuItems.isEmpty()) {
        return
    }

    val expanded = remember { mutableStateOf(false) }

    // use Box to anchor the DropdownMenu
    Box {
        if (showIcon) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = contentDescription,
                modifier = modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = false, radius = touchRadius, color = Color.Unspecified),
                        role = Role.Button,
                        onClick = { expanded.value = true }
                    ),
                tint = iconTintColor
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {

            OverflowMenuItemsContent(menuItems, expanded)
        }
    }
}

@Composable
fun OverflowMenuItemsContent(menuItems: List<OverflowMenuItem>, expanded: MutableState<Boolean>) {
    // determine if there are any icons in the list
    val menuItemsWithLeadingIconCount = menuItems.count { it is OverflowMenuItem.MenuItem && it.hasLeadingIcon() }
    val menuItemsWithTrailingIconCount = menuItems.count { it is OverflowMenuItem.MenuItem && it.hasTrailingIcon() }

    menuItems.forEach { menuItem ->
        val endPadding = if (menuItemsWithTrailingIconCount > 0 && menuItem is OverflowMenuItem.MenuItem) (16.dp) else 0.dp

        when (menuItem) {
            is OverflowMenuItem.MenuItem -> {
                val menuText = menuItem.text()
                DropdownMenuItem(
                    onClick = {
                        menuItem.action()
                        expanded.value = false
                    },
                    text = {
                        Text(text = menuText, modifier = Modifier.padding(end = endPadding))
                    },
                    leadingIcon = when {
                        menuItem.leadingIcon != null -> {
                            { menuItem.leadingIcon?.let { Icon(imageVector = it, contentDescription = null) } }
                        }

                        menuItemsWithLeadingIconCount > 0 -> {
                            // Allocate the space for the leading icon so the text lines up if not all items have leading icons
                            // This is an empty Composable
                            { }
                        }

                        else -> null
                    },
                    trailingIcon = when {
                        menuItem.trailingIcon != null -> {
                            { menuItem.trailingIcon?.let { Icon(imageVector = it, contentDescription = null) } }
                        }

                        menuItemsWithTrailingIconCount > 0 -> {
                            // Allocate the space for the trailing icon so the text lines up if not all items have trailing icons
                            // This is an empty Composable
                            { }
                        }

                        else -> null
                    },
                    modifier = Modifier.defaultMinSize(minWidth = 175.dp)
                )
            }

            is OverflowMenuItem.MenuItemCustom -> {
                val menuText = menuItem.text()
                DropdownMenuItem(
                    onClick = {
                        menuItem.action()
                        expanded.value = false
                    },
                    text = {
                        Text(text = menuText, modifier = Modifier.padding(end = endPadding))
                    },
                    leadingIcon = when {
                        menuItem.leadingContent != null -> {
                            { menuItem.leadingContent?.invoke() }
                        }

                        menuItemsWithLeadingIconCount > 0 -> {
                            // Allocate the space for the leading icon so the text lines up if not all items have leading icons
                            // This is an empty Composable
                            { }
                        }

                        else -> null
                    },
                    trailingIcon = when {
                        menuItem.trailingContent != null -> {
                            { menuItem.trailingContent?.invoke() }
                        }

                        menuItemsWithTrailingIconCount > 0 -> {
                            // Allocate the space for the trailing icon so the text lines up if not all items have trailing icons
                            // This is an empty Composable
                            { }
                        }

                        else -> null
                    },
                    modifier = Modifier.defaultMinSize(minWidth = 175.dp)
                )

            }

            is OverflowMenuItem.Divider -> {
                HorizontalDivider()
            }
        }
    }
}

sealed interface OverflowMenuItem {
    open class MenuItem(
        open val text: @Composable () -> String,
        open val leadingIcon: ImageVector? = null,
        open val trailingIcon: ImageVector? = null,
        open val action: () -> Unit,
    ) : OverflowMenuItem {
        constructor(@StringRes textId: Int, leadingIcon: ImageVector? = null, trailingIcon: ImageVector? = null, action: () -> Unit) : this(
            text = { stringResource(textId) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            action = action
        )

        fun hasLeadingIcon(): Boolean = this.leadingIcon != null
        fun hasTrailingIcon(): Boolean = this.trailingIcon != null
    }

    open class MenuItemCustom(
        open val text: @Composable () -> String,
        open val leadingContent: (@Composable () -> Unit)? = null,
        open val trailingContent: (@Composable () -> Unit)? = null,
        open val action: () -> Unit,
    ) : OverflowMenuItem {
        constructor(@StringRes textId: Int, leadingContent: (@Composable () -> Unit)? = null, trailingContent: (@Composable () -> Unit)? = null, action: () -> Unit) : this(
            text = { stringResource(textId) },
            leadingContent = leadingContent,
            trailingContent = trailingContent,
            action = action
        )

        fun hasLeadingIcon(): Boolean = this.leadingContent != null
        fun hasTrailingIcon(): Boolean = this.trailingContent != null
    }

    open class Divider : OverflowMenuItem
}

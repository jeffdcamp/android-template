package org.jdc.template.ui.compose.menu

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
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
    contentDescription: String? = stringResource(R.string.more_options)
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
                        indication = rememberRipple(bounded = false, radius = touchRadius),
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
        // if there are icons in the list, make sure text without icons are all indented properly
        // 36.dp == 24.dp (icon size) + 12.dp (gap)
        val startPadding = if (menuItemsWithLeadingIconCount > 0 && menuItem is OverflowMenuItem.MenuItem && !menuItem.hasLeadingIcon()) (36.dp) else 0.dp
        val endPadding = if (menuItemsWithTrailingIconCount > 0 && menuItem is OverflowMenuItem.MenuItem && !menuItem.hasTrailingIcon()) (36.dp) else 0.dp

        when (menuItem) {
            is OverflowMenuItem.MenuItem -> {
                val menuText = menuItem.text()
                DropdownMenuItem(
                    onClick = {
                        menuItem.action()
                        expanded.value = false
                    },
                    text = {
                        Text(text = menuText, modifier = Modifier.padding(start = startPadding, end = endPadding))
                    },
                    leadingIcon = if (menuItem.leadingIcon != null) {
                        {
                            menuItem.leadingIcon?.let { Icon(it, contentDescription = null) }
                        }
                    } else null,
                    trailingIcon = if (menuItem.trailingIcon != null) {
                        {
                            menuItem.trailingIcon?.let { Icon(it, contentDescription = null) }
                        }
                    } else null,
                    modifier = Modifier.defaultMinSize(minWidth = 175.dp)
                )

            }
            is OverflowMenuItem.Divider -> HorizontalDivider()
        }
    }
}

sealed interface OverflowMenuItem {
    open class MenuItem(
        open val text: @Composable () -> String,
        open val leadingIcon: ImageVector? = null,
        open val trailingIcon: ImageVector? = null,
        open val action: () -> Unit
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

    open class Divider : OverflowMenuItem
}

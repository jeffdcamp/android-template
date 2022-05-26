package org.jdc.template.ui.compose.appbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.jdc.template.R

@Composable
fun AppBarMenu(menuItems: List<AppBarMenuItem>) {
    val itemsOnBar = menuItems.filter { it !is AppBarMenuItem.OverflowMenuItem }
    val overflowMenuItems = menuItems.filterIsInstance<AppBarMenuItem.OverflowMenuItem>()

    // show items on bar first (in the order received)
    itemsOnBar.forEach {
        when(it) {
            is AppBarMenuItem.Icon -> AppBarIcon(it)
            is AppBarMenuItem.Text -> AppBarText(it)
            else -> error("Unexpected item (filter failed)")
        }
    }

    // Show overflow items
    AppBarOverflowMenu(overflowMenuItems)
}

@Composable
fun AppBarIcon(imageVector: ImageVector, contentDescription: String, action: () -> Unit) {
    IconButton(onClick = action) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun AppBarIcon(menuItem: AppBarMenuItem.Icon) {
    IconButton(onClick = { menuItem.action() }) {
        Icon(
            imageVector = menuItem.imageVector,
            contentDescription = menuItem.text ?: stringResource(menuItem.textId ?: error("Text and TextId are null"))
        )
    }
}

@Composable
fun AppBarText(text: String, action: () -> Unit) {
    TextButton(
        onClick = action,
    ) {
        Text(text)
    }
}

@Composable
fun AppBarText(menuItem: AppBarMenuItem.Text) {
    AppBarText(text = menuItem.text ?: stringResource(menuItem.textId ?: error("Text and TextId are null")), action = menuItem.action)
}

@Composable
fun AppBarOverflowMenu(menuItems: List<AppBarMenuItem.OverflowMenuItem>) {
    if (menuItems.isEmpty()) {
        return
    }

    val expanded = remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded.value = true
    }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.more_options)
        )
    }
    DropdownMenu(
        expanded = expanded.value,
        offset = DpOffset((-40).dp, (-40).dp),
        onDismissRequest = { expanded.value = false }) {

        // determine if there are any icons in the list... if so, make sure text without icons are all indented
        val menuItemsWithIconCount = menuItems.count { it.icon != null }
        val textWithoutIconPadding = if (menuItemsWithIconCount > 0) (36.dp) else 0.dp // 36.dp == 24.dp (icon size) + 12.dp (gap)

        menuItems.forEach { menuItem ->
            val menuText = menuItem.text ?: stringResource(menuItem.textId ?: error("Text and TextId are null"))
            DropdownMenuItem(
                onClick = {
                    menuItem.action()
                    expanded.value = false
                },
                modifier = Modifier.defaultMinSize(minWidth = 175.dp)
            ) {
                if (menuItem.icon != null) {
                    Icon(
                        imageVector = menuItem.icon,
                        contentDescription = menuText,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = menuText,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                } else {
                    Text(
                            text = menuText,
                            modifier = Modifier.padding(start = textWithoutIconPadding)
                        )
                }
            }
        }
    }
}

sealed interface AppBarMenuItem {
    class Icon private constructor(
        val imageVector: ImageVector,
        val text: String?,
        @StringRes val textId: Int?,
        val action: () -> Unit
    ) : AppBarMenuItem {
        constructor(imageVector: ImageVector, text: String, action: () -> Unit) : this(imageVector, text, null, action)
        constructor(imageVector: ImageVector, @StringRes textId: Int, action: () -> Unit) : this(imageVector, null, textId, action)
    }

    class Text private constructor(
        val text: String?,
        @StringRes val textId: Int?,
        val action: () -> Unit
    ) : AppBarMenuItem {
        constructor(text: String, action: () -> Unit) : this(text, null, action)
        constructor(@StringRes textId: Int, action: () -> Unit) : this(null, textId, action)
    }

    class OverflowMenuItem private constructor(
        val text: String?,
        @StringRes val textId: Int?,
        val icon: ImageVector?,
        val action: () -> Unit
    ) : AppBarMenuItem {
        constructor(text: String, icon: ImageVector? = null, action: () -> Unit) : this(text, null, icon, action)
        constructor(@StringRes textId: Int, icon: ImageVector? = null, action: () -> Unit) : this(null, textId, icon, action)
    }
}

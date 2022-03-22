package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
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
            is AppBarMenuItem.IconPainter -> AppBarIcon(it)
            is AppBarMenuItem.Text -> AppBarText(it)
            else -> error("Unexpected item (filter failed)")
        }
    }

    // Show overflow items
    AppBarOverflowMenu(overflowMenuItems)
}

@Composable
fun AppBarIcon(imageVector: ImageVector, contentDescription: String, action: () -> Unit) {
    AppBarIcon(AppBarMenuItem.Icon(imageVector, contentDescription, action))
}

@Composable
fun AppBarIcon(menuItem: AppBarMenuItem.Icon) {
    IconButton(onClick = { menuItem.action() }) {
        Icon(
            imageVector = menuItem.imageVector,
            contentDescription = menuItem.text,
        )
    }
}

@Composable
fun AppBarIcon(menuItem: AppBarMenuItem.IconPainter) {
    IconButton(onClick = { menuItem.action() }) {
        Icon(
            painter = menuItem.painter,
            contentDescription = menuItem.text,
        )
    }
}

@Composable
fun AppBarText(text: String, action: () -> Unit) {
    AppBarText(AppBarMenuItem.Text(text, action))
}

@Composable
fun AppBarText(menuItem: AppBarMenuItem.Text) {
    TextButton(
        onClick = { menuItem.action() },
    ) {
        Text(menuItem.text, color = MaterialTheme.colors.onPrimary)
    }
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
            DropdownMenuItem(onClick = {
                menuItem.action()
                expanded.value = false
            }) {
                if (menuItem.icon != null) {
                    Icon(
                        imageVector = menuItem.icon,
                        contentDescription = menuItem.text,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(text = menuItem.text)
                } else {
                    Text(text = menuItem.text, modifier = Modifier.padding(start = textWithoutIconPadding))
                }
            }
        }
    }
}

sealed class AppBarMenuItem {
    class Icon(val imageVector: ImageVector, val text: String, val action: () -> Unit) : AppBarMenuItem()
    class IconPainter(val painter: Painter, val text: String, val action: () -> Unit) : AppBarMenuItem() // support older image assets
    class Text(val text: String, val action: () -> Unit) : AppBarMenuItem()
    class OverflowMenuItem(val text: String, val icon: ImageVector? = null, val action: () -> Unit) : AppBarMenuItem()
}

package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
            is AppBarMenuItem.TextButton -> AppBarTextButton(it)
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
            contentDescription = menuItem.text()
        )
    }
}

@Composable
fun AppBarText(text: String, colors: ButtonColors? = null, action: () -> Unit) {
    TextButton(
        onClick = action,
        colors = colors ?: ButtonDefaults.textButtonColors()
    ) {
        Text(text)
    }
}

@Composable
fun AppBarText(menuItem: AppBarMenuItem.Text) {
    AppBarText(text = menuItem.text(), colors = menuItem.colors, action = menuItem.action)
}

@Composable
fun AppBarTextButton(text: String, colors: ButtonColors? = null, action: () -> Unit) {
    Row {
        Button(
            onClick = action,
            colors = colors ?: ButtonDefaults.buttonColors(),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 8.dp,
                end = 12.dp,
                bottom = 8.dp
            )
        ) {
            Text(text)
        }

        Spacer(modifier = Modifier.width(4.dp))
    }
}

@Composable
fun AppBarTextButton(menuItem: AppBarMenuItem.TextButton) {
    AppBarTextButton(text = menuItem.text(), action = menuItem.action)
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
        val textWithoutIconPadding = if (menuItemsWithIconCount > 0) 36.dp else 0.dp // 36.dp == 24.dp (icon size) + 12.dp (gap)

        menuItems.forEach { menuItem ->
            val menuText = menuItem.text()
            DropdownMenuItem(
                onClick = {
                    menuItem.action()
                    expanded.value = false
                },
                text = {
                    if (menuItem.icon != null) {
                        Row {
                            Icon(
                                imageVector = menuItem.icon,
                                contentDescription = menuText,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = menuText,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    } else {
                        Text(
                            text = menuText,
                            modifier = Modifier.padding(start = textWithoutIconPadding)
                        )
                    }
                },
                modifier = Modifier.defaultMinSize(minWidth = 175.dp)
            )
        }
    }
}

sealed interface AppBarMenuItem {
    class Icon(val imageVector: ImageVector, val text: @Composable () -> String, val action: () -> Unit) : AppBarMenuItem
    class Text(val text: @Composable () -> String, val colors: ButtonColors? = null, val action: () -> Unit) : AppBarMenuItem
    class TextButton(val text: @Composable () -> String, val colors: ButtonColors? = null, val action: () -> Unit) : AppBarMenuItem
    class OverflowMenuItem(
        val text: @Composable () -> String,
        val icon: ImageVector? = null,
        val action: () -> Unit
    ) : AppBarMenuItem
}

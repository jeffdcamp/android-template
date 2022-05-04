package org.jdc.template.ui.compose.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.jdc.template.R

@Composable
fun OverflowMenu(
    menuItems: List<OverflowMenuItem>,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    if (menuItems.isEmpty()) {
        return
    }

    val expanded = remember { mutableStateOf(false) }

    // use Box to anchor the DropdownMenu
    Box {
        if (showIcon) {
            IconButton(
                onClick = {
                    expanded.value = true
                },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_options)
                )
            }
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {

            // determine if there are any icons in the list... if so, make sure text without icons are all indented
            val menuItemsWithIconCount = menuItems.count { it.icon != null }
            val textWithoutIconPadding = if (menuItemsWithIconCount > 0) (36.dp) else 0.dp // 36.dp == 24.dp (icon size) + 12.dp (gap)

            menuItems.forEach { menuItem ->
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
                            contentDescription = menuItem.text,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(
                            text = menuItem.text,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    } else {
                        Text(text = menuItem.text, modifier = Modifier.padding(start = textWithoutIconPadding))
                    }
                }
            }
        }
    }
}

class OverflowMenuItem(val text: String, val icon: ImageVector? = null, val action: () -> Unit)

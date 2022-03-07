package org.jdc.template.ui.compose

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
import androidx.compose.ui.res.stringResource
import org.jdc.template.R

@Composable
fun AppBarOverflowMenu(menuItems: List<OverflowItem>) {
    if (menuItems.isEmpty()) {
        return
    }

    val expanded = remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded.value = true
    }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.overflow_menu)
        )
    }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }) {
        menuItems.forEach { menuItem ->
            DropdownMenuItem(onClick = {
                menuItem.action()
                expanded.value = false
            }) {
                Text(text = menuItem.text)
            }
        }
    }
}

class OverflowItem(val text: String, val action: () -> Unit)

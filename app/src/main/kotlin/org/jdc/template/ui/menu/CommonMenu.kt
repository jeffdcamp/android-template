package org.jdc.template.ui.menu

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import org.jdc.template.MainNavigationDirections
import org.jdc.template.R
import org.jdc.template.ux.main.NavControllerAmbient

@Composable
fun CommonMenu() {
    var openState: Boolean by remember { mutableStateOf(false) }
    DropdownMenu(
        toggle = {
            IconButton(onClick = { openState = true }) {
                Icon(Icons.Filled.Settings)
            }
        },
        expanded = openState,
        onDismissRequest = { openState = false }
    ) {
        val navController = NavControllerAmbient.current
        DropdownMenuItem(onClick = {
            openState = false
            navController.navigate(MainNavigationDirections.actionGlobalSettingsFragment())
        }) {
            Text(stringResource(id = R.string.settings))
        }
        DropdownMenuItem(onClick = {
            openState = false
            navController.navigate(MainNavigationDirections.actionGlobalAboutFragment())
        }) {
            Text(stringResource(id = R.string.about))
        }
    }
}
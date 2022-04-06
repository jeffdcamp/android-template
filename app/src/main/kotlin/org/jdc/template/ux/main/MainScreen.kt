package org.jdc.template.ux.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jdc.template.R
import org.jdc.template.ui.compose.appnavbar.AppBottomNavigationItem
import org.jdc.template.ui.compose.icons.google.outlined.People
import org.jdc.template.ui.theme.AppTheme

@Composable
fun BottomNav(
    selectedBarItem: NavBarItem?,
    onNavItemClicked: (NavBarItem) -> Unit
) {
    Column {
        Divider()

        val selectedColor: Color = AppTheme.colors.primary
        BottomNavigation(
            backgroundColor = AppTheme.colors.surface,
            contentColor = AppTheme.colors.onSurface,
        ) {
            AppBottomNavigationItem(NavBarItem.PEOPLE, Icons.Outlined.People, R.string.people, selectedBarItem, selectedColor) { onNavItemClicked(it) }
            AppBottomNavigationItem(NavBarItem.ABOUT, Icons.Outlined.Info, R.string.about, selectedBarItem, selectedColor) { onNavItemClicked(it) }
        }
    }
}

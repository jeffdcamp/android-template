package org.jdc.template.ux.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import org.jdc.template.R
import org.jdc.template.ui.compose.appnavbar.AppBottomNavigationItem
import org.jdc.template.ui.compose.icons.google.outlined.People

@Composable
fun BottomNav(
    selectedBarItem: NavBarItem?,
    onNavItemClicked: (NavBarItem) -> Unit
) {
    Column {
        NavigationBar {
            AppBottomNavigationItem(NavBarItem.PEOPLE, Icons.Outlined.People, R.string.people, selectedBarItem) { onNavItemClicked(it) }
            AppBottomNavigationItem(NavBarItem.ABOUT, Icons.Outlined.Info, R.string.about, selectedBarItem) { onNavItemClicked(it) }
        }
    }
}

package org.jdc.template.ux.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import org.jdc.template.R
import org.jdc.template.ui.compose.icons.google.outlined.People
import org.jdc.template.ui.navigation.NavRoute
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.directory.DirectoryRoute

enum class NavBarItem(
    val unselectedImageVector: ImageVector,
    val selectedImageVector: ImageVector,
    val route: NavRoute,
    @StringRes val textResId: Int? = null,
) {
    PEOPLE(Icons.Outlined.People, Icons.Filled.People, DirectoryRoute.createRoute(), R.string.people),
    ABOUT(Icons.Outlined.Info, Icons.Filled.Info, AboutRoute.createRoute(), R.string.about);

    companion object {
        fun getNavBarItemRouteMap(): Map<NavBarItem, NavRoute> {
            return entries.associateWith { item -> item.route }
        }
    }
}
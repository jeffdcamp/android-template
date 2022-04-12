package org.jdc.template.ui.compose.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.NavigationRail
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.jdc.template.R
import org.jdc.template.ui.compose.appnavbar.AppBottomNavigationItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationRailItem
import org.jdc.template.ui.compose.icons.google.outlined.People
import org.jdc.template.ui.theme.AppColor
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.main.NavBarItem

@Composable
internal fun AppScaffold(
    title: String,
    subtitle: String? = null,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    appBarTextColor: Color? = null,
    appBarBackgroundColor: Color? = null,
    autoSizeTitle: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    navBarData: AppNavBarData? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopAppBar(
                title = title,
                subtitle = subtitle,
                appBarTextColor = appBarTextColor,
                appBarBackgroundColor = appBarBackgroundColor,
                autoSizeTitle = autoSizeTitle,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = { onNavigationClick?.invoke() },
                actions = actions,
            )
        },
        bottomBar = navBarData?.getBottomBar() ?: {}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (navBarData?.isNavRail() == true) {
                // Content with NavigationRail
                Row {
                    navBarData.getNavRail()?.invoke()
                    content(innerPadding)
                }
            } else {
                // Content
                content(innerPadding)
            }
        }
    }
}

@Composable
internal fun AppScaffold(
    title: @Composable () -> Unit,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    navBarData: AppNavBarData? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopAppBar(
                title = title,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = { onNavigationClick?.invoke() },
                actions = actions,
            )
        },
        bottomBar = navBarData?.getBottomBar() ?: {}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (navBarData?.isNavRail() == true) {
                // Content with NavigationRail
                Row {
                    navBarData.getNavRail()?.invoke()
                    content(innerPadding)
                }
            } else {
                // Content
                content(innerPadding)
            }
        }
    }
}

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun AboutScaffoldPreview() {
    AppTheme {
        AppScaffold(
            title = "Screen Title",
            actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                }
            }
        ) {
            Text(text = "Content goes here")
        }
    }
}

@Preview(group = "Tablet", widthDp = 700, heightDp = 1000, showBackground = true)
@Composable
private fun AboutScaffoldTabletPreview() {
    val selectedItem = NavBarItem.PEOPLE
    val selectedColor: Color = AppColor.Red700Dark

    val navBarData = AppNavBarData(
        navBarAsRail = true,
        navBar = {
            BottomNavigation(
                backgroundColor = AppTheme.colors.surface,
                contentColor = AppTheme.colors.onSurface,
            ) {
                AppBottomNavigationItem(NavBarItem.PEOPLE, Icons.Outlined.People, selectedItem, selectedColor, R.string.people) {  }
                AppBottomNavigationItem(NavBarItem.ABOUT, Icons.Outlined.Info, selectedItem, selectedColor, R.string.about) {  }
            }
        },
        navRail = {
            NavigationRail {
                AppNavigationRailItem(NavBarItem.PEOPLE, Icons.Outlined.People, selectedItem, selectedColor, R.string.people) {  }
                AppNavigationRailItem(NavBarItem.ABOUT, Icons.Outlined.Info, selectedItem, selectedColor, R.string.about) {  }
            }
        }
    )

    AppTheme {
        AppScaffold(
            title = "Screen Title",
            actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                }
            },
            navBarData = navBarData
        ) {
            Text(text = "Content goes here")
        }
    }
}

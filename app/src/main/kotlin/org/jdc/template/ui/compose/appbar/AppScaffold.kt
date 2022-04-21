package org.jdc.template.ui.compose.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.NavigationRail
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.jdc.template.R
import org.jdc.template.ui.compose.appnavbar.AppBottomNavigationItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationDrawerItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationDrawerLabel
import org.jdc.template.ui.compose.appnavbar.AppNavigationRailItem
import org.jdc.template.ui.compose.appnavbar.PermanentNavigationDrawer
import org.jdc.template.ui.compose.icons.google.outlined.People
import org.jdc.template.ui.theme.AppColor
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.main.NavBarItem

@Composable
fun AppScaffold(
    title: String,
    modifier: Modifier = Modifier,
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
    val appTopAppBar: @Composable (() -> Unit) = {
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
    }

    if (navBarData?.appNavBarType == AppNavBarType.NAV_DRAWER) {
        // NavDrawer
        navBarData.navDrawer()?.invoke {
            Scaffold(
                topBar = appTopAppBar,
                bottomBar = navBarData.bottomBar(),
                modifier = modifier
            ) { innerPadding ->
                AppScaffoldContentWrapper(innerPadding, navBarData, content)
            }
        }

    } else {
        // NavBar / NavRail
        Scaffold(
            topBar = appTopAppBar,
            bottomBar = navBarData?.bottomBar() ?: {},
            modifier = modifier
        ) { innerPadding ->
            AppScaffoldContentWrapper(innerPadding, navBarData, content)
        }
    }
}

@Composable
fun AppScaffold(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    navBarData: AppNavBarData? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val appTopAppBar: @Composable (() -> Unit) = {
        AppTopAppBar(
            title = title,
            navigationIconVisible = navigationIconVisible,
            navigationIcon = navigationIcon,
            onNavigationClick = { onNavigationClick?.invoke() },
            actions = actions,
        )
    }

    if (navBarData?.appNavBarType == AppNavBarType.NAV_DRAWER) {
        // NavDrawer
        navBarData.navDrawer()?.invoke {
            Scaffold(
                topBar = appTopAppBar,
                bottomBar = navBarData.bottomBar(),
                modifier = modifier
            ) { innerPadding ->
                AppScaffoldContentWrapper(innerPadding, navBarData, content)
            }
        }
    } else {
        // NavBar / NavRail
        Scaffold(
            topBar = appTopAppBar,
            bottomBar = navBarData?.bottomBar() ?: {},
            modifier = modifier
        ) { innerPadding ->
            AppScaffoldContentWrapper(innerPadding, navBarData, content)
        }
    }
}

/**
 * Content within a Scaffold for using either
 */
@Composable
private fun AppScaffoldContentWrapper(
    innerPadding: PaddingValues,
    navBarData: AppNavBarData? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(modifier = Modifier.padding(innerPadding)) {
        when (navBarData?.appNavBarType) {
            AppNavBarType.NAV_RAIL -> {
                // Content with NavigationRail
                Row {
                    navBarData.navRail()?.invoke()
                    content(innerPadding)
                }
            }
            else -> {
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
        AppScaffold("Screen Title") {
            Text(text = "Content goes here")
        }
    }
}

@Preview(group = "Navigation", widthDp = 400, heightDp = 800, showBackground = true)
@Composable
private fun AboutScaffoldPhoneWithNavPreview() {
    val navBarData = previewAppNavBarData(NavBarItem.PEOPLE, AppNavBarType.NAV_BAR)

    AppTheme {
        AppScaffold("Screen Title", navBarData = navBarData) {
            Text(text = "Content goes here")
        }
    }
}

@Preview(group = "Navigation", widthDp = 600, heightDp = 1000, showBackground = true)
@Composable
private fun AboutScaffoldTabletWithNavPreview() {
    val navBarData = previewAppNavBarData(NavBarItem.PEOPLE, AppNavBarType.NAV_RAIL)

    AppTheme {
        AppScaffold("Screen Title", navBarData = navBarData) {
            Text(text = "Content goes here")
        }
    }
}

@Preview(group = "Navigation", widthDp = 1200, heightDp = 800, showBackground = true)
@Composable
private fun AboutScaffoldDesktopWithNavPreview() {
    val navBarData = previewAppNavBarData(NavBarItem.PEOPLE, AppNavBarType.NAV_DRAWER)

    AppTheme {
        AppScaffold("Screen Title", navBarData = navBarData) {
            Text(text = "Content goes here")
        }
    }
}

private fun previewAppNavBarData(selectedItem: NavBarItem, appNavBarType: AppNavBarType): AppNavBarData {
    val selectedColor: Color = AppColor.Red700Dark
    return AppNavBarData(
        appNavBarType = appNavBarType,
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
        },
        navDrawer = { appScaffoldContent ->
            PermanentNavigationDrawer(
                drawerContent = {
                    AppNavigationDrawerLabel(stringResource(R.string.app_name))
                    AppNavigationDrawerItem(NavBarItem.PEOPLE, Icons.Outlined.People, selectedItem, selectedColor, R.string.people) {  }
                    AppNavigationDrawerItem(NavBarItem.ABOUT, Icons.Outlined.Info, selectedItem, selectedColor, R.string.about) {  }
                }
            ) {
                appScaffoldContent()
            }
        }
    )
}

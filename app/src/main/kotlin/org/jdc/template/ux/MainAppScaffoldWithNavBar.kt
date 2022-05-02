package org.jdc.template.ux

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.NavigationRail
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.jdc.template.R
import org.jdc.template.ui.compose.appbar.AppNavBarData
import org.jdc.template.ui.compose.appbar.AppNavBarType
import org.jdc.template.ui.compose.appbar.AppScaffold
import org.jdc.template.ui.compose.appnavbar.AppBottomNavigationItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationDrawerItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationDrawerLabel
import org.jdc.template.ui.compose.appnavbar.AppNavigationRailItem
import org.jdc.template.ui.compose.appnavbar.PermanentNavigationDrawer
import org.jdc.template.ui.compose.icons.google.outlined.People
import org.jdc.template.ui.compose.util.rememberWindowSize
import org.jdc.template.ui.compose.util.WindowSize
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.util.ext.requireActivity
import org.jdc.template.ux.main.MainViewModel
import org.jdc.template.ux.main.NavBarItem

@Composable
internal fun MainAppScaffoldWithNavBar(
    title: String,
    subtitle: String? = null,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    appBarTextColor: Color? = null,
    appBarBackgroundColor: Color? = null,
    autoSizeTitle: Boolean = false,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val activity = LocalContext.current.requireActivity()
    val windowSize = activity.rememberWindowSize()
    val viewModel: MainViewModel = hiltViewModel(activity)
    val selectedBarItem by viewModel.selectedNavBarFlow.collectAsState()

    val appbarBarType = when(windowSize) {
        WindowSize.COMPACT -> AppNavBarType.NAV_BAR
        WindowSize.MEDIUM -> AppNavBarType.NAV_RAIL
        WindowSize.EXPANDED -> AppNavBarType.NAV_RAIL
    }

    val navBarData = AppNavBarData(
        appNavBarType = appbarBarType,
        navBar = {
            AppNavigationBar(
                selectedItem = selectedBarItem,
                onNavItemClicked = { viewModel.onNavBarItemSelected(it) }
            )
        },
        navRail = {
            AppNavigationRail(
                selectedItem = selectedBarItem,
                onNavItemClicked = { viewModel.onNavBarItemSelected(it) }
            )
        },
//        navDrawer = { appScaffold ->
//            AppNavigationDrawer(
//                selectedItem = selectedBarItem,
//                onNavItemClicked = { viewModel.onNavBarItemSelected(it) },
//                appScaffoldContent = appScaffold
//            )
//        }
    )

    AppScaffold(
        title = title,
        subtitle = subtitle,
        navigationIconVisible = navigationIconVisible,
        navigationIcon = navigationIcon,
        onNavigationClick = onNavigationClick,
        appBarTextColor = appBarTextColor,
        appBarBackgroundColor = appBarBackgroundColor,
        autoSizeTitle = autoSizeTitle,
        hideNavigation = hideNavigation,
        actions = actions,
        navBarData = navBarData,
        content = content
    )
}

@Composable
private fun AppNavigationBar(
    selectedItem: NavBarItem?,
    onNavItemClicked: (NavBarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Divider()

        val selectedColor: Color = AppTheme.colors.primary
        BottomNavigation(
            backgroundColor = AppTheme.colors.surface,
            contentColor = AppTheme.colors.onSurface,
        ) {
            AppBottomNavigationItem(NavBarItem.PEOPLE, Icons.Outlined.People, selectedItem, selectedColor, R.string.people) { onNavItemClicked(it) }
            AppBottomNavigationItem(NavBarItem.ABOUT, Icons.Outlined.Info, selectedItem, selectedColor, R.string.about) { onNavItemClicked(it) }
        }
    }
}

@Composable
private fun AppNavigationRail(
    selectedItem: NavBarItem?,
    onNavItemClicked: (NavBarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor: Color = AppTheme.colors.primary
    Row(modifier = modifier) {
        NavigationRail {
            AppNavigationRailItem(NavBarItem.PEOPLE, Icons.Outlined.People, selectedItem, selectedColor, R.string.people) { onNavItemClicked(it) }
            AppNavigationRailItem(NavBarItem.ABOUT, Icons.Outlined.Info, selectedItem, selectedColor, R.string.about) { onNavItemClicked(it) }
        }
    }
}

@Composable
private fun AppNavigationDrawer(
    selectedItem: NavBarItem?,
    onNavItemClicked: (NavBarItem) -> Unit,
    appScaffoldContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor: Color = AppTheme.colors.primary
    PermanentNavigationDrawer(
        drawerContent = {
            AppNavigationDrawerLabel(stringResource(R.string.app_name))
            AppNavigationDrawerItem(NavBarItem.PEOPLE, Icons.Outlined.People, selectedItem, selectedColor, R.string.people) { onNavItemClicked(it) }
            AppNavigationDrawerItem(NavBarItem.ABOUT, Icons.Outlined.Info, selectedItem, selectedColor, R.string.about) { onNavItemClicked(it) }
        }
    ) {
        appScaffoldContent()
    }
}
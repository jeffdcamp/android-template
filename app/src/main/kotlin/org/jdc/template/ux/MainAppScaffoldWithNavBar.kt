package org.jdc.template.ux

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.R
import org.jdc.template.ui.compose.appbar.AppNavBarData
import org.jdc.template.ui.compose.appbar.AppNavBarType
import org.jdc.template.ui.compose.appbar.AppScaffoldAndNavigation
import org.jdc.template.ui.compose.appnavbar.AppBottomNavigationItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationDrawerItem
import org.jdc.template.ui.compose.appnavbar.AppNavigationDrawerLabel
import org.jdc.template.ui.compose.appnavbar.AppNavigationRailItem
import org.jdc.template.ui.compose.util.rememberWindowSize
import org.jdc.template.util.ext.requireActivity
import org.jdc.template.ux.main.MainViewModel
import org.jdc.template.ux.main.NavBarItem

@Composable
fun MainAppScaffoldWithNavBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    MainAppScaffoldWithNavBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIconVisible = navigationIconVisible,
        navigationIcon = navigationIcon,
        onNavigationClick = onNavigationClick,
        hideNavigation = hideNavigation,
        actions = actions,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}

@Composable
fun MainAppScaffoldWithNavBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    val activity = LocalContext.current.requireActivity()
    val windowSize = activity.rememberWindowSize()
    val viewModel: MainViewModel = hiltViewModel(activity)
    val selectedBarItem by viewModel.selectedNavBarFlow.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // TopAppBar
    val topAppBar: @Composable (() -> Unit) = {
        TopAppBar(
            title = title,
            navigationIcon = if (!navigationIconVisible) {
                {}
            } else {
                {
                    IconButton(onClick = { onNavigationClick?.invoke() }) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = stringResource(id = R.string.back),
                            modifier = Modifier
                        )
                    }
                }
            },
            actions = {
                if (actions != null) {
                    actions()
                }
            },
            scrollBehavior = scrollBehavior
        )
    }

    // Navigation (support Bottom / Rail / Drawer)
    val navBarData = AppNavBarData(
        appNavBarType = AppNavBarType.byWindowSize(windowSize),
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
        navDrawer = { appScaffold ->
            AppNavigationDrawer(
                selectedItem = selectedBarItem,
                onNavItemClicked = { viewModel.onNavBarItemSelected(it) },
                appScaffoldContent = appScaffold
            )
        }
    )

    // Scaffold
    AppScaffoldAndNavigation(
        topAppBar = topAppBar,
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection).imePadding(),
        hideNavigation = hideNavigation,
        navBarData = navBarData,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = contentWindowInsets,
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
        NavigationBar {
            NavBarItem.entries.forEach { item ->
                AppBottomNavigationItem(item, item.unselectedImageVector, item.selectedImageVector, selectedItem, item.textResId) { onNavItemClicked(it) }
            }
        }
    }
}

@Composable
private fun AppNavigationRail(
    selectedItem: NavBarItem?,
    onNavItemClicked: (NavBarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        NavigationRail {
            NavBarItem.entries.forEach { item ->
                AppNavigationRailItem(item, item.unselectedImageVector, item.selectedImageVector, selectedItem, item.textResId) { onNavItemClicked(it) }
            }
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
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet {
                AppNavigationDrawerLabel(stringResource(R.string.app_name))

                NavBarItem.entries.forEach { item ->
                    AppNavigationDrawerItem(item, item.unselectedImageVector, item.selectedImageVector, selectedItem, item.textResId) { onNavItemClicked(it) }
                }
            }
        },
        modifier = modifier
    ) {
        appScaffoldContent()
    }
}
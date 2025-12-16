package org.jdc.template.ux

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import org.jdc.template.R
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.ux.main.NavBarItem

@Composable
fun MainAppScaffoldWithNavBar(
    navigator: Navigation3Navigator,
    title: String,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    MainAppScaffoldWithNavBar(
        navigator = navigator,
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
    navigator: Navigation3Navigator,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    when {
        hideNavigation -> {
            AppScaffold(
                modifier = modifier,
                title = title,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = onNavigationClick,
                actions = actions,
                floatingActionButton = floatingActionButton,
                content = content
            )
        }
        else -> {
            NavigationSuiteScaffold(
                layoutType = NavigationSuiteScaffoldDefaults.navigationSuiteType(currentWindowAdaptiveInfo()),
                navigationSuiteItems = {
                    NavBarItem.entries.forEach { navBarItem: NavBarItem ->
                        val isSelected = navBarItem.route == navigator.getSelectedTopLevelRoute()
                        val imageVector = if (isSelected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector
                        item(
                            selected = isSelected,
                            icon = { Icon(imageVector = imageVector, contentDescription = null) },
                            label = navBarItem.textResId?.let { { Text(stringResource(it)) } },
                            onClick = { navigator.navigateTopLevel(navBarItem.route, reselected = isSelected) },
                        )
                    }
                }
            ) {
                AppScaffold(
                    modifier = modifier,
                    title = title,
                    navigationIconVisible = navigationIconVisible,
                    navigationIcon = navigationIcon,
                    onNavigationClick = onNavigationClick,
                    actions = actions,
                    floatingActionButton = floatingActionButton,
                    floatingActionButtonPosition = floatingActionButtonPosition,
                    contentWindowInsets = contentWindowInsets,
                    content = content
                )
            }
        }
    }
}

@Composable
private fun AppScaffold(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val appScaffoldModifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding()

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
            actions = { actions?.invoke(this) },
            scrollBehavior = scrollBehavior
        )
    }

    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = contentWindowInsets,
        modifier = appScaffoldModifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

package org.jdc.template.ux

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jdc.template.R
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator
import org.jdc.template.ux.main.NavBarItem

@Composable
fun MainAppScaffoldWithNavBar(
    navigator: Navigation3Navigator,
    title: String,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideTopAppBar: Boolean = false,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = { Text(text = title) },
        modifier = modifier,
        navigationIconVisible = navigationIconVisible,
        navigationIcon = navigationIcon,
        onNavigationClick = onNavigationClick,
        hideTopAppBar = hideTopAppBar,
        hideNavigation = hideNavigation,
        actions = actions,
        floatingActionButton = floatingActionButton,
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
    hideTopAppBar: Boolean = false,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val windowSize = currentWindowSize()
    val navigationSuiteScaffoldState = rememberNavigationSuiteScaffoldState(initialValue = if (hideNavigation) NavigationSuiteScaffoldValue.Hidden else NavigationSuiteScaffoldValue.Visible)

    val imeVisible = WindowInsets.isImeVisible
    LaunchedEffect(hideNavigation, imeVisible) {
        if (hideNavigation || imeVisible) navigationSuiteScaffoldState.hide() else navigationSuiteScaffoldState.show()
    }

    val layoutType = getNavigationSuiteType(windowSize.toDpSize())

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavBarItem.entries.forEach { navBarItem: NavBarItem ->
                val isSelected = navBarItem.route == navigator.getSelectedTopLevelRoute()
                val imageVector = if (isSelected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector
                item(
                    selected = isSelected,
                    icon = { Icon(imageVector = imageVector, contentDescription = null) },
                    label = navBarItem.textResId?.let { { Text(stringResource(it)) } },
                    onClick = { navigator.navigateTopLevel(navBarItem.route, reselected = isSelected) }
                )
            }
        },
        layoutType = layoutType,
        state = navigationSuiteScaffoldState
    ) {
        AppScaffold(
            title = title,
            modifier = modifier,
            navigationIconVisible = navigationIconVisible,
            navigationIcon = navigationIcon,
            onNavigationClick = onNavigationClick,
            hideTopAppBar = hideTopAppBar,
            actions = actions,
            floatingActionButton = floatingActionButton,
            content = content
        )
    }
}

@Composable
private fun AppScaffold(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideTopAppBar: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
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
            actions = { actions?.invoke(this) },
            scrollBehavior = scrollBehavior
        )
    }

    Scaffold(
        topBar = if (hideTopAppBar) {{}} else topAppBar,
        floatingActionButton = floatingActionButton,
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

private fun getNavigationSuiteType(windowSize: DpSize): NavigationSuiteType {
    // defaults from Nav3
//    val layoutType = NavigationSuiteScaffoldDefaults.navigationSuiteType(currentWindowAdaptiveInfo())

    return if (windowSize.width >= 600.dp) {
        NavigationSuiteType.NavigationRail
    } else {
        NavigationSuiteType.NavigationBar
    }
}

@Composable
private fun IntSize.toDpSize(): DpSize = with(LocalDensity.current) {
    DpSize(width.toDp(), height.toDp())
}

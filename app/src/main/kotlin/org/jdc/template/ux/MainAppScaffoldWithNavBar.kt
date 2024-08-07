package org.jdc.template.ux

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import org.jdc.template.R
import org.jdc.template.ui.compose.util.WindowSize
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
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
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
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    val activity = LocalContext.current.requireActivity()
    val viewModel: MainViewModel = hiltViewModel(activity)
    val selectedBarItem by viewModel.selectedNavBarFlow.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val windowSize = currentWindowSize()

    // TopAppBar
    val topAppBar: @Composable (() -> Unit) = {
        TopAppBar(
            title = title,
            navigationIcon = if (!navigationIconVisible) {
                {}
            } else {
                {
                    IconButton(onClick = dropUnlessResumed { onNavigationClick?.invoke() }) {
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

    NavigationSuiteScaffold(
        layoutType = if (hideNavigation) NavigationSuiteType.None else getNavigationSuiteType(windowSize.toDpSize()),
        navigationSuiteItems = {
            NavBarItem.entries.forEach { navBarItem ->
                val selected = selectedBarItem == navBarItem
                val imageVector = if (selected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector

                item(
                    selected = selected,
                    icon = { Icon(imageVector = imageVector, contentDescription = null) },
                    label = if (navBarItem.textResId != null) {
                        { Text(stringResource(navBarItem.textResId), maxLines = 1) }
                    } else {
                        null
                    },
                    onClick = { viewModel.onNavBarItemSelected(navBarItem) }
                )
            }
        },
    ) {
        AppScaffold(
            topAppBar = topAppBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            contentWindowInsets = contentWindowInsets,
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            content = content
        )
    }
}

@Composable
private fun AppScaffold(
    topAppBar: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit,
    floatingActionButtonPosition: FabPosition,
    contentWindowInsets: WindowInsets,
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable () -> Unit,
) {
    val windowSize = LocalContext.current.requireActivity().rememberWindowSize()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val appScaffoldModifier = if (isLandscape && windowSize == WindowSize.COMPACT) {
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .navigationBarsPadding() // prevent FAB and top app bar from being covered by OS nav bar in landscape
            .imePadding()
    } else {
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding()
    }

    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = contentWindowInsets,
        modifier = appScaffoldModifier,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

/**
 * Per <a href="https://m3.material.io/components/navigation-drawer/guidelines">Material Design 3 guidelines</a>,
 * the selection of the appropriate navigation component should be contingent on the available
 * window size:
 * - Bottom Bar for compact window sizes (below 600dp)
 * - Navigation Rail for medium and expanded window sizes up to 1240dp (between 600dp and 1240dp)
 * - Navigation Drawer to window size above 1240dp
 */
fun getNavigationSuiteType(windowSize: DpSize): NavigationSuiteType {
    return if (windowSize.width > 1240.dp) {
        NavigationSuiteType.NavigationDrawer
    } else if (windowSize.width >= 600.dp) {
        NavigationSuiteType.NavigationRail
    } else {
        NavigationSuiteType.NavigationBar
    }
}

@Composable
private fun IntSize.toDpSize(): DpSize = with(LocalDensity.current) {
    DpSize(width.toDp(), height.toDp())
}

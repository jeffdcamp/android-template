package org.jdc.template.ux

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalWideNavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailState
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.ui.compose.util.WindowSize
import org.jdc.template.ui.compose.util.rememberWindowSize
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.util.ext.requireActivity
import org.jdc.template.ux.main.NavBarItem

@Composable
fun MainAppScaffoldWithNavBar(
    navigator: Navigation3Navigator<NavKey>,
    title: String,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable (navSuiteType: NavigationSuiteType, isWideNavRailCollapsedType: Boolean, railExpanded: Boolean) -> Unit = { _, _, _ -> },
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
        content = content
    )
}

@Composable
fun MainAppScaffoldWithNavBar(
    navigator: Navigation3Navigator<NavKey>,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable (navSuiteType: NavigationSuiteType, isWideNavRailCollapsedType: Boolean, railExpanded: Boolean) -> Unit = { _, _, _ -> },
    content: @Composable () -> Unit,
) {
    val navSuiteType = if (hideNavigation) NavigationSuiteType.None else NavigationSuiteScaffoldDefaults.navigationSuiteType(currentWindowAdaptiveInfo())
    val state = rememberNavigationSuiteScaffoldState()
    val scope = rememberCoroutineScope()
    val railState = rememberWideNavigationRailState()
    val railExpanded = railState.currentValue == WideNavigationRailValue.Expanded
    val isWideNavRailCollapsedType = navSuiteType == NavigationSuiteType.WideNavigationRailCollapsed
    val menuButton = @Composable { NavigationRailMenuButton(railExpanded, scope, railState) }

    Surface(
        modifier = modifier
    ) {
        // Use NavigationSuiteScaffoldLayout so that we can customize the NavigationSuite.
        NavigationSuiteScaffoldLayout(
            navigationSuiteType = navSuiteType,
            state = state,
            primaryActionContent = { floatingActionButton(navSuiteType, isWideNavRailCollapsedType, railExpanded) },
            navigationSuite = {
                // Pass in a custom modal rail to substitute the default collapsed wide nav rail.
                if (isWideNavRailCollapsedType) {
                    ModalWideNavigationRail(
                        state = railState,
                        header = {
                            Column {
                                menuButton()
                                Spacer(Modifier.padding(vertical = 8.dp))
                                floatingActionButton(navSuiteType, true, railExpanded)
                            }
                        },
                        expandedHeaderTopPadding = 64.dp,
                    ) {
                        NavBarItem.entries.forEach { navBarItem: NavBarItem ->
                            val isSelected = navBarItem.route == navigator.getSelectedTopLevelRoute()
                            WideNavigationRailItem(
                                icon = { Icon(imageVector = if (isSelected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector, contentDescription = null) },
                                label = navBarItem.textResId?.let{ { Text(stringResource(it)) } },
                                selected = isSelected,
                                onClick = { navigator.navigateTopLevel(navBarItem.route, reselected = isSelected) },
                                railExpanded = railExpanded,
                            )
                        }
                    }
                } else {
                    NavigationSuite(
                        navigationSuiteType = navSuiteType,
                        primaryActionContent = { floatingActionButton(navSuiteType, false, railExpanded) },
                    ) {
                        NavBarItem.entries.forEach { navBarItem: NavBarItem ->
                            val isSelected = navBarItem.route == navigator.getSelectedTopLevelRoute()
                            NavigationSuiteItem(
                                navigationSuiteType = navSuiteType,
                                icon = { Icon(imageVector = if (isSelected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector, contentDescription = null) },
                                label = navBarItem.textResId?.let{ { Text(stringResource(it)) } },
                                selected = isSelected,
                                onClick = { navigator.navigateTopLevel(navBarItem.route, reselected = isSelected) },
                            )
                        }
                    }
                }
            }
        ) {
            AppScaffold(
                title = title,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = onNavigationClick,
                actions = actions,
                content = content
            )
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
    content: @Composable () -> Unit,
) {
    val windowSize = LocalContext.current.requireActivity().rememberWindowSize()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val appScaffoldModifier = if (isLandscape && windowSize == WindowSize.COMPACT) {
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding()
    } else {
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding()
    }

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
        modifier = appScaffoldModifier,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

@Composable
private fun NavigationRailMenuButton(
    railExpanded: Boolean,
    scope: CoroutineScope,
    railState: WideNavigationRailState
) {
    IconButton(
        modifier =
            Modifier
                .padding(start = 24.dp, bottom = 8.dp)
                .semantics {
                    stateDescription = if (railExpanded) "Expanded" else "Collapsed"
                },
        onClick = { scope.launch { railState.toggle() } }
    ) {
        if (railExpanded) {
            Icon(Icons.AutoMirrored.Filled.MenuOpen, "Collapse rail")
        } else {
            Icon(Icons.Filled.Menu, "Expand rail")
        }
    }
}

@Composable
fun DefaultNav3FloatingActionButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    navSuiteType: NavigationSuiteType,
    isWideNavRailCollapsedType: Boolean,
    railExpanded: Boolean,
    modifier: Modifier = Modifier,
    text: String? = null,
) {
    val startPadding =
        if (navSuiteType == NavigationSuiteType.ShortNavigationBarMedium) {
            0.dp
        } else {
            24.dp
        }

    ExtendedFloatingActionButton(
        modifier = modifier.padding(start = startPadding), // .then(animateFAB),
        onClick = { onClick() },
        expanded = if (text.isNullOrBlank()) {
            false
        } else {
            if (isWideNavRailCollapsedType) railExpanded else navSuiteType == NavigationSuiteType.WideNavigationRailExpanded
        },
        icon = icon,
        text = if (text != null) { { Text(text) } } else { {} }
    )
}

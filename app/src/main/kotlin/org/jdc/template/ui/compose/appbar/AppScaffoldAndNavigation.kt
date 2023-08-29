package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppScaffoldAndNavigation(
    topAppBar: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    hideNavigation: Boolean = false,
    navBarData: AppNavBarData? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    snackbarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    when {
        hideNavigation -> {
            Scaffold(
                topBar = topAppBar,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                containerColor = containerColor,
                snackbarHost = snackbarHost,
                contentWindowInsets = contentWindowInsets,
                modifier = modifier
            ) { innerPadding ->
                AppScaffoldContentWrapper(innerPadding, content = content)
            }
        }
        navBarData?.appNavBarType == AppNavBarType.NAV_DRAWER -> {
            // NavDrawer
            navBarData.navDrawer()?.invoke {
                Scaffold(
                    topBar = topAppBar,
                    bottomBar = navBarData.bottomBar(),
                    floatingActionButton = floatingActionButton,
                    floatingActionButtonPosition = floatingActionButtonPosition,
                    containerColor = containerColor,
                    snackbarHost = snackbarHost,
                    contentWindowInsets = contentWindowInsets,
                    modifier = modifier
                ) { innerPadding ->
                    AppScaffoldContentWrapper(innerPadding, navBarData, content)
                }
            }
        }
        else -> {
            // NavBar / NavRail
            Scaffold(
                topBar = topAppBar,
                bottomBar = navBarData?.bottomBar() ?: {},
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                containerColor = containerColor,
                snackbarHost = snackbarHost,
                contentWindowInsets = contentWindowInsets,
                modifier = modifier
            ) { innerPadding ->
                AppScaffoldContentWrapper(innerPadding, navBarData, content)
            }
        }
    }
}

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

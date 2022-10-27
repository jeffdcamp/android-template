package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppScaffold(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    appBarColors: TopAppBarColors? = null,
    autoSizeTitle: Boolean = false,
    hideTopAppBar: Boolean = false,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    navBarData: AppNavBarData? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val appTopAppBar: @Composable (() -> Unit) = if (!hideTopAppBar) {
        {
            AppTopAppBar(
                title = title,
                subtitle = subtitle,
                colors = appBarColors,
                autoSizeTitle = autoSizeTitle,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = { onNavigationClick?.invoke() },
                actions = actions,
            )
        }
    } else {
        {}
    }

    AppScaffoldAndNavigation(
        topAppBar = appTopAppBar,
        modifier = modifier,
        hideNavigation = hideNavigation,
        navBarData = navBarData,
        content = content
    )
}

@Composable
fun AppScaffold(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    appBarColors: TopAppBarColors? = null,
    hideTopAppBar: Boolean = false,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    navBarData: AppNavBarData? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val appTopAppBar: @Composable (() -> Unit) = if (!hideTopAppBar) {
        {
            AppTopAppBar(
                title = title,
                colors = appBarColors,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = { onNavigationClick?.invoke() },
                actions = actions,
            )
        }
    } else {
        {}
    }

    AppScaffoldAndNavigation(
        topAppBar = appTopAppBar,
        modifier = modifier,
        hideNavigation = hideNavigation,
        navBarData = navBarData,
        content = content
    )
}

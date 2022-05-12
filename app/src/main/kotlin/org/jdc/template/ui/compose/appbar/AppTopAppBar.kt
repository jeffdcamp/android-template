package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import org.jdc.template.R
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@Composable
internal fun AppTopAppBar(
    title: String,
    subtitle: String? = null,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    appBarTextColor: Color? = null,
    appBarBackgroundColor: Color? = null,
    autoSizeTitle: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    AppTopAppBar(
        title = {
            AppBarTitle(
                title = title,
                subtitle = subtitle,
                color = appBarTextColor ?: Color.Unspecified,
                autoSizeTitle = autoSizeTitle
            )
        },
        navigationIconVisible = navigationIconVisible,
        navigationIcon = navigationIcon,
        onNavigationClick = onNavigationClick,
        appBarBackgroundColor = appBarBackgroundColor,
        actions = actions
    )
}

@Composable
internal fun AppTopAppBar(
    title: @Composable () -> Unit,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    navigationIconContentDescription: String = stringResource(id = R.string.back),
    onNavigationClick: (() -> Unit)? = null,
    appBarBackgroundColor: Color? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        title = title,
        backgroundColor = appBarBackgroundColor ?: MaterialTheme.colors.primarySurface,
        navigationIcon = if (!navigationIconVisible) null else {
            {
                IconButton(onClick = { onNavigationClick?.invoke() }) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                        modifier = Modifier
                    )
                }
            }
        },
        actions = {
            // Wrapping content so that the action icons have the same color as the navigation icon and title.
            if (actions != null) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    content = { actions() }
                )
            }
        }
    )
}

@PreviewDefault
@Composable
private fun AboutTopAppBarPreview() {
    AppTheme {
        AppTopAppBar(
            title = "App Bar Title",
            subtitle = "Test",
            onNavigationClick = {},
            actions = {
                IconButton(onClick = { }) { Icon(imageVector = Icons.Outlined.Info, contentDescription = null) }
                IconButton(onClick = { }) { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) }
            }
        )
    }
}
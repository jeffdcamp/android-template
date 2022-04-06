package org.jdc.template.ui.compose.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.jdc.template.R
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
    SmallTopAppBar(
        title = title,
        navigationIcon = if (!navigationIconVisible) null else {
            IconButton(onClick = { onNavigationClick?.invoke() }) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    modifier = Modifier
                )
            }
        },
        actions = {
            // Wrapping content so that the action icons have the same color as the navigation icon and title.
            if (actions != null) {
                actions()
            }
        }
    )
}

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
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
package org.jdc.template.ui.compose.appbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.theme.AppTheme

@Composable
internal fun AppScaffold(
    title: String,
    subtitle: String? = null,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    appBarTextColor: Color? = null,
    appBarBackgroundColor: Color? = null,
    autoSizeTitle: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = title,
                subtitle = subtitle,
                appBarTextColor = appBarTextColor,
                appBarBackgroundColor = appBarBackgroundColor,
                autoSizeTitle = autoSizeTitle,
                navigationIcon = navigationIcon,
                onNavigationClick = { if (onNavigationClick == null) navController?.popBackStack() else onNavigationClick() },
                actions = actions,
            )
        },
    ) {
        content(it)
    }
}

@Composable
internal fun AppScaffold(
    title: @Composable () -> Unit,
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                onNavigationClick = { if (onNavigationClick == null) navController?.popBackStack() else onNavigationClick() },
                actions = actions,
            )
        },
    ) {
        content(it)
    }
}

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun AboutScaffoldPreview() {
    AppTheme {
        AppScaffold(
            title = "Screen Title",
            actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                }
            }
        ) {
            Text(text = "Content goes here")
        }
    }
}
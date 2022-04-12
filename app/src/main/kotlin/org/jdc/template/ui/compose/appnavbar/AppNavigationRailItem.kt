package org.jdc.template.ui.compose.appnavbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun <T : Enum<T>> ColumnScope.AppNavigationRailItem(
    navBarItem: T,
    imageVector: ImageVector,
    selectedBarItem: T?,
    @StringRes textResId: Int? = null,
    text: String? = null,
    onNavItemClicked: (T) -> Unit
) {
    NavigationRailItem(
        icon = { Icon(imageVector, contentDescription = null) },
        label = {
            when {
                text != null -> Text(text, maxLines = 1)
                textResId != null -> Text(stringResource(textResId), maxLines = 1)
            }
        },
        selected = selectedBarItem == navBarItem,
        onClick = {
            onNavItemClicked(navBarItem)
        }
    )
}

package org.jdc.template.ui.compose.appnavbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun <T : Enum<T>> RowScope.AppBottomNavigationItem(
    navBarItem: T,
    imageVector: ImageVector,
    selectedBarItem: T?,
    @StringRes textResId: Int? = null,
    text: String? = null,
    onNavItemClicked: (T) -> Unit
) {
    NavigationBarItem(
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

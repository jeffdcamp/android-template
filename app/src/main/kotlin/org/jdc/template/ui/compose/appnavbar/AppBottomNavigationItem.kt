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
    unselectedImageVector: ImageVector,
    selectedImageVector: ImageVector,
    selectedBarItem: T?,
    @StringRes textResId: Int? = null,
    text: String? = null,
    onNavItemClicked: (T) -> Unit
) {
    val selected = selectedBarItem == navBarItem

    NavigationBarItem(
        icon = { if (selected) Icon(selectedImageVector, contentDescription = null) else Icon(unselectedImageVector, contentDescription = null) },
        label = {
            when {
                text != null -> Text(text, maxLines = 1)
                textResId != null -> Text(stringResource(textResId), maxLines = 1)
            }
        },
        selected = selected,
        onClick = {
            onNavItemClicked(navBarItem)
        }
    )
}

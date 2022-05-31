package org.jdc.template.ui.compose.appnavbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import org.jdc.template.ui.theme.AppTheme

@Composable
fun <T : Enum<T>> RowScope.AppBottomNavigationItem(
    navBarItem: T,
    imageVector: ImageVector,
    selectedBarItem: T?,
    selectedColor: Color,
    @StringRes textResId: Int? = null,
    text: String? = null,
    onNavItemClicked: (T) -> Unit
) {
    BottomNavigationItem(
        icon = { Icon(imageVector, contentDescription = null) },
        label = {
            when {
                text != null -> Text(text, maxLines = 1)
                textResId != null -> Text(stringResource(textResId), maxLines = 1)
            }
        },
        selected = selectedBarItem == navBarItem,
        selectedContentColor = selectedColor,
        unselectedContentColor = AppTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
        onClick = {
            onNavItemClicked(navBarItem)
        }
    )
}

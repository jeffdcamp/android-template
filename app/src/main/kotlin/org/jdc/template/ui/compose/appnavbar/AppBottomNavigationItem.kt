package org.jdc.template.ui.compose.appnavbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun <T : Enum<T>> RowScope.AppBottomNavigationItem(
    navBarItem: T,
    imageVector: ImageVector,
    @StringRes textResId: Int,
    selectedBarItem: T?,
    selectedColor: Color,
    onNavItemClicked: (T) -> Unit
) {
    BottomNavigationItem(
        icon = { Icon(imageVector, contentDescription = null) },
        label = {
            Text(
                stringResource(textResId),
                maxLines = 1
            )
        },
        selected = selectedBarItem == navBarItem,
        selectedContentColor = selectedColor,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        onClick = {
            onNavItemClicked(navBarItem)
        }
    )
}

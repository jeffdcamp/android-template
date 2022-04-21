package org.jdc.template.ui.compose.appnavbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun <T : Enum<T>> AppNavigationDrawerItem(
    navBarItem: T,
    imageVector: ImageVector,
    selectedBarItem: T?,
    selectedColor: Color,
    @StringRes textResId: Int? = null,
    text: String? = null,
    onNavItemClicked: (T) -> Unit
) {
    NavigationDrawerItem(
        icon = { Icon(imageVector, contentDescription = null) },
        label = {
            when {
                text != null -> Text(text, maxLines = 1)
                textResId != null -> Text(stringResource(textResId), maxLines = 1)
            }
        },
        selected = selectedBarItem == navBarItem,
        selectedColor = selectedColor,
        onClick = {
            onNavItemClicked(navBarItem)
        },
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    )
}

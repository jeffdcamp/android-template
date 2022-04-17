package org.jdc.template.ui.compose.appnavbar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Deprecated("Use PermanentNavigationDrawer from M3 library (temp workaround for M2)")
@Composable
fun PermanentNavigationDrawer(
    drawerContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    drawerShape: Shape = RectangleShape,
    drawerTonalElevation: Dp = 0.0.dp, //DrawerDefaults.PermanentDrawerElevation,
    drawerContainerColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    content: @Composable () -> Unit
) {
    val drawerWidth = 360.0.dp // NavigationDrawerTokens.ContainerWidth
    Row(modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .sizeIn(maxWidth = drawerWidth),
            shape = drawerShape,
            color = drawerContainerColor,
            contentColor = drawerContentColor
        ) {
            Column(Modifier.fillMaxSize(), content = drawerContent)
        }
        Box {
            content()
        }
    }
}

@Deprecated("Use NavigationDrawerItem from M3 library (temp workaround for M2)")
@Composable
fun NavigationDrawerItem(
    label: @Composable () -> Unit,
    selected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    badge: (@Composable () -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.large, // NavigationDrawerTokens.ActiveIndicatorShape.toShape(),
    color: Color = MaterialTheme.colors.surface,
    colorText: Color = MaterialTheme.colors.primary,
    badgeColor: Color = MaterialTheme.colors.secondary,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Surface(
        selected = selected,
        onClick = onClick,
        modifier = modifier
            .height(56.0.dp) // NavigationDrawerTokens.ActiveIndicatorHeight)
            .fillMaxWidth(),
        shape = shape,
        color = color, //colors.containerColor(selected).value,
        interactionSource = interactionSource,
    ) {
        Row(
            Modifier.padding(start = 16.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                val iconColor = if (selected) selectedColor else LocalContentColor.current
                CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
                Spacer(Modifier.width(12.dp))
            }
            Box(Modifier.weight(1f)) {
                val labelColor = if (selected) selectedColor else LocalContentColor.current
                CompositionLocalProvider(LocalContentColor provides labelColor, content = label)
            }
            if (badge != null) {
                Spacer(Modifier.width(12.dp))
                CompositionLocalProvider(LocalContentColor provides badgeColor, content = badge)
            }
        }
    }
}
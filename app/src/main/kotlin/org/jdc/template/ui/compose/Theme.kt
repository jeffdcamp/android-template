package org.jdc.template.ui.compose

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val darkPalette = darkColorPalette(
    primary = material_red_500,
    primaryVariant = material_red_500_dark,
    onPrimary = material_white,
    secondary = material_blue_500,
    //secondaryVariant = material_blue_500_dark,
    onSecondary = material_white,
    error = material_red_300,
    onError = material_black,
    surface = material_black,
    onSurface = material_white,
    background = material_grey_900,
    onBackground = material_white
)

private val lightPalette = lightColorPalette(
    primary = material_red_700,
    primaryVariant = material_red_700_dark,
    onPrimary = material_white,
    secondary = material_blue_700,
    secondaryVariant = material_blue_700_dark,
    onSecondary = material_white,
    error = material_red_900,
    onError = material_white,
    surface = material_white,
    onSurface = material_black,
    background = material_white,
    onBackground = material_black
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) darkPalette else lightPalette
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
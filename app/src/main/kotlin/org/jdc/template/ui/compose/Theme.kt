package org.jdc.template.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkPalette = darkColors(
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

private val LightPalette = lightColors(
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
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkPalette else LightPalette
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
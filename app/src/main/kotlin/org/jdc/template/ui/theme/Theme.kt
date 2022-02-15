package org.jdc.template.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

@Suppress("LongParameterList")
class AppColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color,
    isLight: Boolean,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var primaryVariant by mutableStateOf(primaryVariant, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var secondaryVariant by mutableStateOf(secondaryVariant, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    fun toMaterialColors(): Colors {
        return Colors(
            primary,
            primaryVariant,
            secondary,
            secondaryVariant,
            background,
            surface,
            error,
            onPrimary,
            onSecondary,
            onBackground,
            onSurface,
            onError,
            isLight
        )
    }

    fun updateColorsFrom(colors: AppColors) {
        primary = colors.primary
        primaryVariant = colors.primaryVariant
        secondary = colors.secondary
        secondaryVariant = colors.secondaryVariant
        background = colors.background
        surface = colors.surface
        error = colors.error
        onPrimary = colors.onPrimary
        onSecondary = colors.onSecondary
        onBackground = colors.onBackground
        onSurface = colors.onSurface
        onError = colors.onError
        isLight = colors.isLight
    }
}

private val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColorPalette provided")
}

object AppTheme {
    val colors: AppColors
        @Composable get() = LocalAppColors.current

//    val typography: Typography
//        @Composable
//        @ReadOnlyComposable
//        get() = MaterialTheme.typography

//    val shapes: Shapes
//        @Composable
//        @ReadOnlyComposable
//        get() = MaterialTheme.shapes

    // Dimensions
    val isTablet: Boolean
        @Composable get() {
            return LocalConfiguration.current.smallestScreenWidthDp >= 600
        }

    // other customizations
//    val switchColors: SwitchColors
//        @Composable get() = SwitchDefaults.colors(
//            checkedThumbColor = colors.primary,
//            uncheckedThumbColor = colors.uncheckedSwitchThumb,
//        )
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) darkColors() else lightColors()

    val colorPalette = remember { colors }
    colorPalette.updateColorsFrom(colors)

    CompositionLocalProvider(
        LocalAppColors provides colorPalette,
    ) {
        MaterialTheme(
            colors = colors.toMaterialColors(),
            content = content,
        )
    }
}

fun lightColors(): AppColors {
    return AppColors(
        primary = MaterialColor.Red700,
        primaryVariant = AppColor.Red700Dark,
        secondary = MaterialColor.Blue700,
        secondaryVariant = AppColor.Blue700Dark,
        background = Color.White,
        surface = Color.White,
        error = MaterialColor.Red900,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.Black,
        onSurface = Color.Black,
        onError = Color.White,
        isLight = true
    )
}

fun darkColors(): AppColors {
    return AppColors(
        primary = MaterialColor.Red500,
        primaryVariant = AppColor.Red500Dark,
        secondary = MaterialColor.Blue500,
        secondaryVariant = AppColor.Blue500Dark,
        background = MaterialColor.Grey800,
        surface = MaterialColor.Grey900,
        error = MaterialColor.Red300,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
        onError = Color.White,
        isLight = false
    )
}

package org.jdc.template.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Suppress("LongParameterList")
class AppColors(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,
    inversePrimary: Color,
    secondary: Color,
    onSecondary: Color,
    secondaryContainer: Color,
    onSecondaryContainer: Color,
    tertiary: Color,
    onTertiary: Color,
    tertiaryContainer: Color,
    onTertiaryContainer: Color,
    background: Color,
    onBackground: Color,
    surface: Color,
    onSurface: Color,
    surfaceVariant: Color,
    onSurfaceVariant: Color,
    inverseSurface: Color,
    inverseOnSurface: Color,
    error: Color,
    onError: Color,
    errorContainer: Color,
    onErrorContainer: Color,
    outline: Color,
    outlineVariant: Color,
    scrim: Color,
    surfaceDim: Color,
    surfaceBright: Color,
    surfaceContainerLowest: Color,
    surfaceContainerLow: Color,
    surfaceContainer: Color,
    surfaceContainerHigh: Color,
    surfaceContainerHighest: Color,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
        internal set
    var onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
        internal set
    var inversePrimary by mutableStateOf(inversePrimary, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var secondaryContainer by mutableStateOf(secondaryContainer, structuralEqualityPolicy())
        internal set
    var onSecondaryContainer by mutableStateOf(onSecondaryContainer, structuralEqualityPolicy())
        internal set
    var tertiary by mutableStateOf(tertiary, structuralEqualityPolicy())
        internal set
    var onTertiary by mutableStateOf(onTertiary, structuralEqualityPolicy())
        internal set
    var tertiaryContainer by mutableStateOf(tertiaryContainer, structuralEqualityPolicy())
        internal set
    var onTertiaryContainer by mutableStateOf(onTertiaryContainer, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
        internal set
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant, structuralEqualityPolicy())
        internal set
    var inverseSurface by mutableStateOf(inverseSurface, structuralEqualityPolicy())
        internal set
    var inverseOnSurface by mutableStateOf(inverseOnSurface, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var errorContainer by mutableStateOf(errorContainer, structuralEqualityPolicy())
        internal set
    var onErrorContainer by mutableStateOf(onErrorContainer, structuralEqualityPolicy())
        internal set
    var outline by mutableStateOf(outline, structuralEqualityPolicy())
        internal set
    var outlineVariant by mutableStateOf(outlineVariant, structuralEqualityPolicy())
        internal set
    var scrim by mutableStateOf(scrim, structuralEqualityPolicy())
        internal set
    var surfaceDim by mutableStateOf(surfaceDim, structuralEqualityPolicy())
        internal set
    var surfaceBright by mutableStateOf(surfaceBright, structuralEqualityPolicy())
        internal set
    var surfaceContainerLowest by mutableStateOf(surfaceContainerLowest, structuralEqualityPolicy())
        internal set
    var surfaceContainerLow by mutableStateOf(surfaceContainerLow, structuralEqualityPolicy())
        internal set
    var surfaceContainer by mutableStateOf(surfaceContainer, structuralEqualityPolicy())
        internal set
    var surfaceContainerHigh by mutableStateOf(surfaceContainerHigh, structuralEqualityPolicy())
        internal set
    var surfaceContainerHighest by mutableStateOf(surfaceContainerHighest, structuralEqualityPolicy())
        internal set

    fun toMaterialColors(): ColorScheme {
        return ColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            inversePrimary = inversePrimary,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            tertiary = tertiary,
            onTertiary = onTertiary,
            tertiaryContainer = tertiaryContainer,
            onTertiaryContainer = onTertiaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceTint = primary, // this is copied from lightColorScheme(...)
            inverseSurface = inverseSurface,
            inverseOnSurface = inverseOnSurface,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline,
            outlineVariant = outlineVariant,
            scrim = scrim,
            surfaceDim = surfaceDim,
            surfaceBright = surfaceBright,
            surfaceContainerLowest = surfaceContainerLowest,
            surfaceContainerLow = surfaceContainerLow,
            surfaceContainer = surfaceContainer,
            surfaceContainerHigh = surfaceContainerHigh,
            surfaceContainerHighest = surfaceContainerHighest,
        )
    }

    fun updateColorsFrom(colors: AppColors) {
        primary = colors.primary
        onPrimary = colors.onPrimary
        primaryContainer = colors.primaryContainer
        onPrimaryContainer = colors.onPrimaryContainer
        inversePrimary = colors.inversePrimary
        secondary = colors.secondary
        onSecondary = colors.onSecondary
        secondaryContainer = colors.secondaryContainer
        onSecondaryContainer = colors.onSecondaryContainer
        tertiary = colors.tertiary
        onTertiary = colors.onTertiary
        tertiaryContainer = colors.tertiaryContainer
        onTertiaryContainer = colors.onTertiaryContainer
        background = colors.background
        onBackground = colors.onBackground
        surface = colors.surface
        onSurface = colors.onSurface
        surfaceVariant = colors.surfaceVariant
        onSurfaceVariant = colors.onSurfaceVariant
        inverseSurface = colors.inverseSurface
        inverseOnSurface = colors.inverseOnSurface
        error = colors.error
        onError = colors.onError
        errorContainer = colors.errorContainer
        onErrorContainer = colors.onErrorContainer
        outline = colors.outline
        outlineVariant = colors.outlineVariant
        scrim = colors.scrim
        surfaceDim = colors.surfaceDim
        surfaceBright = colors.surfaceBright
        surfaceContainerLowest = colors.surfaceContainerLowest
        surfaceContainerLow = colors.surfaceContainerLow
        surfaceContainer = colors.surfaceContainer
        surfaceContainerHigh = colors.surfaceContainerHigh
        surfaceContainerHighest = colors.surfaceContainerHighest
    }
}

private val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColorPalette provided")
}

object AppTheme {
    val colorScheme: AppColors
        @Composable get() = LocalAppColors.current

//    val typography: Typography
//        @Composable
//        @ReadOnlyComposable
//        get() = MaterialTheme.typography

//    val shapes: Shapes
//        @Composable
//        @ReadOnlyComposable
//        get() = MaterialTheme.shapes


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
    dynamicTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors: AppColors = when {
        dynamicTheme && Build.VERSION.SDK_INT >= 31 -> if (darkTheme) dynamicDarkColorScheme(LocalContext.current).toAppColors() else dynamicLightColorScheme(LocalContext.current).toAppColors()
        else -> if (darkTheme) AppPalette.darkColors() else AppPalette.lightColors()
    }

    val colorPalette = remember { colors }
    colorPalette.updateColorsFrom(colors)

    CompositionLocalProvider(
        LocalAppColors provides colorPalette,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColors(),
            content = content,
        )
    }
}

private fun ColorScheme.toAppColors(): AppColors {
    return AppColors(
        primary = this.primary,
        onPrimary = this.onPrimary,
        primaryContainer = this.primaryContainer,
        onPrimaryContainer = this.onPrimaryContainer,
        inversePrimary = this.inversePrimary,
        secondary = this.secondary,
        onSecondary = this.onSecondary,
        secondaryContainer = this.secondaryContainer,
        onSecondaryContainer = this.onSecondaryContainer,
        tertiary = this.tertiary,
        onTertiary = this.onTertiary,
        tertiaryContainer = this.tertiaryContainer,
        onTertiaryContainer = this.onTertiaryContainer,
        background = this.background,
        onBackground = this.onBackground,
        surface = this.surface,
        onSurface = this.onSurface,
        surfaceVariant = this.surfaceVariant,
        onSurfaceVariant = this.onSurfaceVariant,
        inverseSurface = this.inverseSurface,
        inverseOnSurface = this.inverseOnSurface,
        error = this.error,
        onError = this.onError,
        errorContainer = this.errorContainer,
        onErrorContainer = this.onErrorContainer,
        outline = this.outline,
        outlineVariant = this.outlineVariant,
        scrim = this.scrim,
        surfaceDim = this.surfaceDim,
        surfaceBright = this.surfaceBright,
        surfaceContainerLowest = this.surfaceContainerLowest,
        surfaceContainerLow = this.surfaceContainerLow,
        surfaceContainer = this.surfaceContainer,
        surfaceContainerHigh = this.surfaceContainerHigh,
        surfaceContainerHighest = this.surfaceContainerHighest,
    )
}

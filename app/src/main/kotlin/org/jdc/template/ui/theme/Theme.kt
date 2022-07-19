package org.jdc.template.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

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
    surfaceTint: Color,
    onSurfaceVariant: Color,
    inverseSurface: Color,
    inverseOnSurface: Color,
    error: Color,
    onError: Color,
    errorContainer: Color,
    onErrorContainer: Color,
    outline: Color,
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
    var surfaceTint by mutableStateOf(surfaceTint, structuralEqualityPolicy())
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
            surfaceTint = surfaceTint,
            inverseSurface = inverseSurface,
            inverseOnSurface = inverseOnSurface,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline
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
        surfaceTint = colors.surfaceTint
        inverseSurface = colors.inverseSurface
        inverseOnSurface = colors.inverseOnSurface
        error = colors.error
        onError = colors.onError
        errorContainer = colors.errorContainer
        onErrorContainer = colors.onErrorContainer
        outline = colors.outline
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
            colorScheme = colors.toMaterialColors(),
            content = content,
        )
    }
}

fun lightColors(): AppColors {
    return AppColors(
        primary = AppPalette.md_theme_light_primary,
        onPrimary = AppPalette.md_theme_light_onPrimary,
        primaryContainer = AppPalette.md_theme_light_primaryContainer,
        onPrimaryContainer = AppPalette.md_theme_light_onPrimaryContainer,
        inversePrimary = AppPalette.md_theme_light_inversePrimary,
        secondary = AppPalette.md_theme_light_secondary,
        onSecondary = AppPalette.md_theme_light_onSecondary,
        secondaryContainer = AppPalette.md_theme_light_secondaryContainer,
        onSecondaryContainer = AppPalette.md_theme_light_onSecondaryContainer,
        tertiary = AppPalette.md_theme_light_tertiary,
        onTertiary = AppPalette.md_theme_light_onTertiary,
        tertiaryContainer = AppPalette.md_theme_light_tertiaryContainer,
        onTertiaryContainer = AppPalette.md_theme_light_onTertiaryContainer,
        background = AppPalette.md_theme_light_background,
        onBackground = AppPalette.md_theme_light_onBackground,
        surface = AppPalette.md_theme_light_surface,
        onSurface = AppPalette.md_theme_light_onSurface,
        surfaceVariant = AppPalette.md_theme_light_surfaceVariant,
        onSurfaceVariant = AppPalette.md_theme_light_onSurfaceVariant,
        surfaceTint = AppPalette.md_theme_light_surfaceVariant, // todo fix when surfaceTint is generated in m3 color
        inverseSurface = AppPalette.md_theme_light_inverseSurface,
        inverseOnSurface = AppPalette.md_theme_light_inverseOnSurface,
        error = AppPalette.md_theme_light_error,
        onError = AppPalette.md_theme_light_onError,
        errorContainer = AppPalette.md_theme_light_errorContainer,
        onErrorContainer = AppPalette.md_theme_light_onErrorContainer,
        outline = AppPalette.md_theme_light_outline
    )
}

fun darkColors(): AppColors {
    return AppColors(
        primary = AppPalette.md_theme_dark_primary,
        onPrimary = AppPalette.md_theme_dark_onPrimary,
        primaryContainer = AppPalette.md_theme_dark_primaryContainer,
        onPrimaryContainer = AppPalette.md_theme_dark_onPrimaryContainer,
        inversePrimary = AppPalette.md_theme_dark_inversePrimary,
        secondary = AppPalette.md_theme_dark_secondary,
        onSecondary = AppPalette.md_theme_dark_onSecondary,
        secondaryContainer = AppPalette.md_theme_dark_secondaryContainer,
        onSecondaryContainer = AppPalette.md_theme_dark_onSecondaryContainer,
        tertiary = AppPalette.md_theme_dark_tertiary,
        onTertiary = AppPalette.md_theme_dark_onTertiary,
        tertiaryContainer = AppPalette.md_theme_dark_tertiaryContainer,
        onTertiaryContainer = AppPalette.md_theme_dark_onTertiaryContainer,
        background = AppPalette.md_theme_dark_background,
        onBackground = AppPalette.md_theme_dark_onBackground,
        surface = AppPalette.md_theme_dark_surface,
        onSurface = AppPalette.md_theme_dark_onSurface,
        surfaceVariant = AppPalette.md_theme_dark_surfaceVariant,
        onSurfaceVariant = AppPalette.md_theme_dark_onSurfaceVariant,
        surfaceTint = AppPalette.md_theme_dark_surfaceVariant, // todo fix when surfaceTint is generated in m3 color
        inverseSurface = AppPalette.md_theme_dark_inverseSurface,
        inverseOnSurface = AppPalette.md_theme_dark_inverseOnSurface,
        error = AppPalette.md_theme_dark_error,
        onError = AppPalette.md_theme_dark_onError,
        errorContainer = AppPalette.md_theme_dark_errorContainer,
        onErrorContainer = AppPalette.md_theme_dark_onErrorContainer,
        outline = AppPalette.md_theme_dark_outline
    )
}

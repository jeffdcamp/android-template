package org.jdc.template.ui.compose.util

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Opinionated set of viewport breakpoints
 *     - Compact: Phones
 *     - Medium: Foldable, Tablet
 *     - Expanded: Large Tablet, Desktop
 *
 * More info:
 *     - https://m3.material.io/foundations/adaptive-design/large-screens/overview
 *     - https://material.io/archive/guidelines/layout/responsive-ui.html
 */
enum class WindowSize {
    COMPACT, // Phone
    MEDIUM, // Foldable, Tablet
    EXPANDED, // Extra large tablet, Desktop
}

/**
 * Remembers the [WindowSize] class for the window corresponding to the current window metrics.
 */
@Composable
fun Activity.rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val windowSize = calculateWindowSizeClass(this)
    return remember(windowSize, configuration) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            when(windowSize.heightSizeClass) {
                WindowHeightSizeClass.Compact -> WindowSize.COMPACT
                WindowHeightSizeClass.Medium -> WindowSize.MEDIUM
                WindowHeightSizeClass.Expanded -> WindowSize.EXPANDED
                else -> WindowSize.COMPACT
            }
        } else {
            when(windowSize.widthSizeClass) {
                WindowWidthSizeClass.Compact -> WindowSize.COMPACT
                WindowWidthSizeClass.Medium -> WindowSize.MEDIUM
                WindowWidthSizeClass.Expanded -> WindowSize.EXPANDED
                else -> WindowSize.COMPACT
            }
        }
    }
}
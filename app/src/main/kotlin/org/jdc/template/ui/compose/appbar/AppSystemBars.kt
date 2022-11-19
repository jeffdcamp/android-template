package org.jdc.template.ui.compose.appbar

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

/**
 * Handle the colors of the OS system bars (statusbar and navbar) (for M3, Light/Dark themes, Dynamic themes)
 */
@Composable
fun HandleColorForSystemBars(bottomNavBarVisible: Boolean) {
    if (Build.VERSION.SDK_INT >= 26) {
        // top OS bar with clock
        val statusBarColor = MaterialTheme.colorScheme.surface

        // bottom OS bar (OS navigation items)
        // bottom OS nav bar should match App BottomNavigationBar ONLY if it is visible
        val navBarColor = if (bottomNavBarVisible) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.surface

        // set colors
        val view = LocalView.current
        val activity  = view.context as Activity
        activity.window.navigationBarColor = navBarColor.toArgb()
        activity.window.statusBarColor = statusBarColor.toArgb()

        // Make sure the status bar icons/text are visible
        val darkTheme = isSystemInDarkTheme()
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = !darkTheme
    }
}
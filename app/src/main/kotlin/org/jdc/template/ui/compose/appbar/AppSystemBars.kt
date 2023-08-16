package org.jdc.template.ui.compose.appbar

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import co.touchlab.kermit.Logger

/**
 * Handle the colors of the OS system bars (statusbar and navbar) (for M3, Light/Dark themes, Dynamic themes)
 *
 * Notes:
 * 1. WindowCompat.setDecorFitsSystemWindows(window, false) should be place in MainActivity.onCreate()
 * 2. This function should be called from the top of AppTheme() { }
 */
@Composable
fun HandleSystemBarColors(darkTheme: Boolean) {
    val view = LocalView.current
    val activity = view.context.getActivity() ?: return

    if (Build.VERSION.SDK_INT < 23) {
        activity.window.statusBarColor = Color.BLACK
        activity.window.navigationBarColor = Color.BLACK
    } else if (Build.VERSION.SDK_INT in 23..26) {
        activity.window.statusBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
        activity.window.navigationBarColor = Color.BLACK
    } else {
        activity.window.statusBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme

        activity.window.navigationBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = !darkTheme
    }
}

private fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> {
        Logger.e { "No Activity Found" }
        null
    }
}

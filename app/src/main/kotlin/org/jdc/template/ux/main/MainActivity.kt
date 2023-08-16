package org.jdc.template.ux.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets, including IME animations (OS statusbar and nav bar colors is handled by app)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen()

        setContent {
            val uiState = viewModel.uiState
            val theme by uiState.selectedAppThemeFlow.collectAsStateWithLifecycle()

            val darkTheme = when(theme?.displayThemeType) {
                DisplayThemeType.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                DisplayThemeType.LIGHT -> false
                DisplayThemeType.DARK -> true
                null -> isSystemInDarkTheme()
            }

            val dynamicTheme = when(theme?.dynamicTheme) {
                true -> true
                else -> false
            }

            AppTheme(darkTheme, dynamicTheme) {
                MainScreen()
            }
        }
    }
}

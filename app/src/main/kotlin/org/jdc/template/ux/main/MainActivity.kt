package org.jdc.template.ux.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Force the 3-button navigation bar to be transparent
            // See: https://developer.android.com/develop/ui/views/layout/edge-to-edge#create-transparent
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            val viewModel: MainViewModel = koinViewModel()
            viewModel.startup()

            val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

            when(val uiState = uiState) {
                MainUiState.Loading -> {}
                is MainUiState.Ready -> {
                    val theme = uiState.selectedAppTheme
                    val darkTheme = when(theme.displayThemeType) {
                        DisplayThemeType.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                        DisplayThemeType.LIGHT -> false
                        DisplayThemeType.DARK -> true
                    }

                    val dynamicTheme = when(theme.dynamicTheme) {
                        true -> true
                        else -> false
                    }

                    // Update the edge to edge configuration to match the theme
                    // This is the same parameters as the default enableEdgeToEdge call, but we manually
                    // resolve whether or not to show dark theme using uiState, since it can be different
                    // than the configuration's dark theme value based on the user preference.
                    DisposableEffect(darkTheme) {
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT,
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                lightScrim,
                                darkScrim,
                            ) { darkTheme },
                        )
                        onDispose {}
                    }


                    AppTheme(darkTheme, dynamicTheme) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

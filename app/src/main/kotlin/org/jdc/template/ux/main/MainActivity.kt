package org.jdc.template.ux.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle any deep links
        intent.dataString?.let { data ->
            viewModel.handleDeepLink(data)
        }

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

            LaunchedEffect(Unit) {
                viewModel.startup()
            }

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

                    AppTheme(darkTheme, dynamicTheme) {
                        MainScreen(viewModel)
                    }
                }
            }
        }
    }
}

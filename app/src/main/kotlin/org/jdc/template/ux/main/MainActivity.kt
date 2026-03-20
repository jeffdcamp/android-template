package org.jdc.template.ux.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.Firebase
import com.google.firebase.appdistribution.InterruptionLevel
import com.google.firebase.appdistribution.appDistribution
import org.jdc.template.R
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle any deep links
        intent.dataString?.let { data ->
            viewModel.handleDeepLink(data)
        }

        installSplashScreen()

        Firebase.appDistribution.showFeedbackNotification(
            R.string.app_distribution_feedback_text,
            InterruptionLevel.DEFAULT,
        )

        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Force the 3-button navigation bar to be transparent
            // See: https://developer.android.com/develop/ui/views/layout/edge-to-edge#create-transparent
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
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

                    // Update the edge to edge configuration to match the theme
                    SideEffect {
                        val barStyle = if (darkTheme) {
                            SystemBarStyle.dark(Color.TRANSPARENT)
                        } else {
                            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                        }

                        enableEdgeToEdge(
                            statusBarStyle = barStyle,
                            navigationBarStyle = barStyle
                        )
                    }

                    AppTheme(darkTheme, dynamicTheme) {
                        MainScreen(viewModel)
                    }
                }
            }
        }
    }
}

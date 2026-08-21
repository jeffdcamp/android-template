package org.jdc.template.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object SettingsUiUtil {
    @Composable
    fun topAppBarColors(): TopAppBarColors {
        return TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    }

    @Composable
    fun scaffoldContainerColor(): Color {
        return MaterialTheme.colorScheme.surfaceContainer
    }
}

package org.jdc.template.ux.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.data.DisplayThemeType

data class MainUiState(
    val themeFlow: StateFlow<DisplayThemeType?> = MutableStateFlow(null)
)

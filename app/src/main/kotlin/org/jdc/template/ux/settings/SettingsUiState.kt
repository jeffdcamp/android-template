package org.jdc.template.ux.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.dialog.DialogUiState

data class SettingsUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val currentThemeTitleFlow: StateFlow<String?> = MutableStateFlow(null),
    val currentLastInstalledVersionCodeFlow: StateFlow<String?> = MutableStateFlow(null),
    val rangeFlow: StateFlow<String?> = MutableStateFlow(null),
    val dynamicThemeFlow: StateFlow<Boolean> = MutableStateFlow(false),
    val sortByLastNameFlow: StateFlow<Boolean> = MutableStateFlow(false),

    // Events
    val onThemeSettingClick: () -> Unit = {},
    val onLastInstalledVersionCodeClick: () -> Unit = {},
    val onRangeClick: () -> Unit = {},
    val dismissSetLastInstalledVersionCodeDialog: () -> Unit = {},
    val setDynamicTheme: (checked: Boolean) -> Unit = {},
    val setSortByLastName: (checked: Boolean) -> Unit = {},
)

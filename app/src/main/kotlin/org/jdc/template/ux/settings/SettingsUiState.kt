package org.jdc.template.ux.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.dialog.DialogUiState

data class SettingsUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val currentThemeTitleFlow: StateFlow<String?> = MutableStateFlow(null),
    val currentLastInstalledVersionCodeFlow: StateFlow<String?> = MutableStateFlow(null),
    val sortByLastNameFlow: StateFlow<Boolean> = MutableStateFlow(false),

    // Events
    val onThemeSettingClicked: () -> Unit = {},
    val onLastInstalledVersionCodeClicked: () -> Unit = {},
    val dismissSetLastInstalledVersionCodeDialog: () -> Unit = {},
    val setSortByLastName: (checked: Boolean) -> Unit = {},
)

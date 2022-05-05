package org.jdc.template.ux.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.ui.compose.dialog.InputDialogData
import org.jdc.template.ui.compose.dialog.RadioDialogData

data class SettingsUiState(
    // Data
    val themeRadioDialogDataFlow: StateFlow<RadioDialogData<DisplayThemeType>?> = MutableStateFlow(null),
    val lastInstalledVersionCodeDialogDataFlow: StateFlow<InputDialogData?> = MutableStateFlow(null),
    val currentThemeTitleFlow: StateFlow<String?> = MutableStateFlow(null),
    val currentLastInstalledVersionCodeFlow: StateFlow<String?> = MutableStateFlow(null),
    val sortByLastNameFlow: StateFlow<Boolean> = MutableStateFlow(false),

    // Events
    val setTheme: (theme: DisplayThemeType) -> Unit = {},
    val onThemeSettingClicked: () -> Unit = {},
    val dismissThemeDialog: () -> Unit = {},
    val onLastInstalledVersionCodeClicked: () -> Unit = {},
    val setLastInstalledVersionCode: (value: String) -> Unit = {},
    val dismissSetLastInstalledVersionCodeDialog: () -> Unit = {},
    val setSortByLastName: (checked: Boolean) -> Unit = {},
)

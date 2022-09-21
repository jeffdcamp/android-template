package org.jdc.template.ux.settings

import android.app.Application
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.InputDialogUiState
import org.jdc.template.ui.compose.dialog.RadioDialogDataItem
import org.jdc.template.ui.compose.dialog.RadioDialogDataItems
import org.jdc.template.ui.compose.dialog.RadioDialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.util.ext.stateInDefault
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val application: Application,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    val uiState = SettingsUiState(
        dialogUiStateFlow = dialogUiStateFlow,
        currentThemeTitleFlow = settingsRepository.themeFlow.map { theme -> theme.getString(application) }.stateInDefault(viewModelScope, null),
        currentLastInstalledVersionCodeFlow = settingsRepository.lastInstalledVersionCodeFlow.map { versionCode -> versionCode.toString() }.stateInDefault(viewModelScope, null),
        sortByLastNameFlow = settingsRepository.directorySortByLastNameFlow.stateInDefault(viewModelScope, false),

        onThemeSettingClicked = ::onThemeSettingClicked,

        onLastInstalledVersionCodeClicked = ::onLastInstalledVersionCodeClicked,
        setSortByLastName = ::setSortByLastName
    )

    private fun onThemeSettingClicked() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val radioItems = DisplayThemeType.values().map { RadioDialogDataItem(it) { it.getString(application) } }

        dialogUiStateFlow.value = RadioDialogUiState(
            items = RadioDialogDataItems(radioItems, currentTheme),
            title = { stringResource(R.string.theme) },
            onConfirm = { theme ->
                settingsRepository.setThemeAsync(theme)
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun onLastInstalledVersionCodeClicked() = viewModelScope.launch {
        val currentValue = settingsRepository.getLastInstalledVersionCode()
        dialogUiStateFlow.value = InputDialogUiState(
            title = { "Version Code" },
            initialTextFieldText = { currentValue.toString() },
            onConfirm = { setLastInstalledVersionCode(it) },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun setLastInstalledVersionCode(value: String) {
        value.toIntOrNull()?.let {
            settingsRepository.setLastInstalledVersionCodeAsync(it)
        }
        dismissDialog(dialogUiStateFlow)
    }

    private fun setSortByLastName(checked: Boolean) {
        settingsRepository.setSortByLastNameAsync(checked)
    }
}

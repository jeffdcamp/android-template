package org.jdc.template.ux.settings

import android.app.Application
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
import org.jdc.template.ui.compose.dialog.InputDialogData
import org.jdc.template.ui.compose.dialog.RadioDialogData
import org.jdc.template.ui.compose.dialog.RadioDialogDataItem
import org.jdc.template.ui.compose.dialog.RadioDialogDataItems
import org.jdc.template.util.ext.stateInDefault
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val application: Application,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val themeRadioDialogDataFlow = MutableStateFlow(RadioDialogData<DisplayThemeType>())
    private val lastInstalledVersionCodeDialogDataFlow = MutableStateFlow(InputDialogData())

    val uiState = SettingsUiState(
        themeRadioDialogDataFlow = themeRadioDialogDataFlow,
        lastInstalledVersionCodeDialogDataFlow = lastInstalledVersionCodeDialogDataFlow,
        currentThemeTitleFlow = settingsRepository.themeFlow.map { theme -> theme.getString(application) }.stateInDefault(viewModelScope, null),
        currentLastInstalledVersionCodeFlow = settingsRepository.lastInstalledVersionCodeFlow.map { versionCode -> versionCode.toString() }.stateInDefault(viewModelScope, null),
        sortByLastNameFlow = settingsRepository.directorySortByLastNameFlow.stateInDefault(viewModelScope, false),

        setTheme = ::setTheme,
        onThemeSettingClicked = ::onThemeSettingClicked,
        dismissThemeDialog = ::dismissThemeDialog,

        onLastInstalledVersionCodeClicked = ::onLastInstalledVersionCodeClicked,
        setLastInstalledVersionCode = ::setLastInstalledVersionCode,
        dismissSetLastInstalledVersionCodeDialog = ::dismissSetLastInstalledVersionCodeDialog,
        setSortByLastName = ::setSortByLastName
    )

    private fun onThemeSettingClicked() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val radioItems = DisplayThemeType.values().map { RadioDialogDataItem(it, it.getString(application)) }

        themeRadioDialogDataFlow.value = RadioDialogData(
            visible = true,
            title = application.getText(R.string.theme).toString(),
            items = RadioDialogDataItems(radioItems, currentTheme)
        )
    }

    private fun setTheme(theme: DisplayThemeType) = viewModelScope.launch {
        settingsRepository.setThemeAsync(theme)
        dismissThemeDialog()
    }

    private fun dismissThemeDialog() {
        themeRadioDialogDataFlow.value = RadioDialogData(visible = false)
    }

    private fun onLastInstalledVersionCodeClicked() = viewModelScope.launch {
        val currentValue = settingsRepository.getLastInstalledVersionCode()
        lastInstalledVersionCodeDialogDataFlow.value = InputDialogData(true, "Version Code", currentValue.toString())
    }

    private fun setLastInstalledVersionCode(value: String) {
        value.toIntOrNull()?.let {
            settingsRepository.setLastInstalledVersionCodeAsync(it)
        }
        dismissSetLastInstalledVersionCodeDialog()
    }

    private fun dismissSetLastInstalledVersionCodeDialog() {
        lastInstalledVersionCodeDialogDataFlow.value = InputDialogData(visible = false)
    }

    private fun setSortByLastName(checked: Boolean) {
        settingsRepository.setSortByLastNameAsync(checked)
    }
}

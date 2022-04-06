package org.jdc.template.ux.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _themeRadioDialogDataFlow = MutableStateFlow(RadioDialogData<DisplayThemeType>())
    val themeRadioDialogDataFlow: StateFlow<RadioDialogData<DisplayThemeType>> = _themeRadioDialogDataFlow

    private val _lastInstalledVersionCodeDialogDataFlow = MutableStateFlow(InputDialogData())
    val lastInstalledVersionCodeDialogDataFlow: StateFlow<InputDialogData> = _lastInstalledVersionCodeDialogDataFlow

    val currentThemeTitleFlow: StateFlow<String?> = settingsRepository.themeFlow.map { theme -> theme.getString(application) }.stateInDefault(viewModelScope, null)

    val currentLastInstalledVersionCodeFlow: StateFlow<String?> = settingsRepository.lastInstalledVersionCodeFlow.map { versionCode -> versionCode.toString() }.stateInDefault(viewModelScope, null)

    val sortByLastNameFlow: StateFlow<Boolean> = settingsRepository.directorySortByLastNameFlow.stateInDefault(viewModelScope, false)

    fun onThemeSettingClicked() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val radioItems = DisplayThemeType.values().map { RadioDialogDataItem(it, it.getString(application)) }

        _themeRadioDialogDataFlow.value = RadioDialogData(
            visible = true,
            title = application.getText(R.string.theme).toString(),
            items = RadioDialogDataItems(radioItems, currentTheme)
        )
    }

    fun setTheme(theme: DisplayThemeType) = viewModelScope.launch {
        settingsRepository.setThemeAsync(theme)
        dismissThemeDialog()
    }

    fun dismissThemeDialog() {
        _themeRadioDialogDataFlow.value = RadioDialogData(visible = false)
    }

    fun onLastInstalledVersionCodeClicked() = viewModelScope.launch {
        val currentValue = settingsRepository.getLastInstalledVersionCode()
        _lastInstalledVersionCodeDialogDataFlow.value = InputDialogData(true, "Version Code", currentValue.toString())
    }

    fun setLastInstalledVersionCode(value: String) {
        value.toIntOrNull()?.let {
            settingsRepository.setLastInstalledVersionCodeAsync(it)
        }
        dismissSetLastInstalledVersionCodeDialog()
    }

    fun dismissSetLastInstalledVersionCodeDialog() {
        _lastInstalledVersionCodeDialogDataFlow.value = InputDialogData(visible = false)
    }

    fun setSortByLastName(checked: Boolean) {
        settingsRepository.setSortByLastNameAsync(checked)
    }
}

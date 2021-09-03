package org.jdc.template.ux.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.repository.SettingsRepository
import org.jdc.template.ui.compose.dialog.RadioDialogData
import org.jdc.template.ui.compose.dialog.RadioDialogDataItem
import org.jdc.template.ui.compose.dialog.RadioDialogDataItems
import org.jdc.template.ui.compose.dialog.InputDialogData
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val application: Application,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _themeRadioDialogData = MutableStateFlow(RadioDialogData<DisplayThemeType>())
    val themeRadioDialogData: StateFlow<RadioDialogData<DisplayThemeType>> = _themeRadioDialogData

    private val _lastInstalledVersionCodeDialogData = MutableStateFlow(InputDialogData())
    val lastInstalledVersionCodeDialogData: StateFlow<InputDialogData> = _lastInstalledVersionCodeDialogData

    val currentThemeTitleFlow: Flow<String>
        get() = settingsRepository.themeFlow.map { theme ->
            theme.getString(application)
        }

    val currentLastInstalledVersionCodeFlow: Flow<String>
        get() = settingsRepository.lastInstalledVersionCodeFlow.map { versionCode ->
            versionCode.toString()
        }

    val sortByLastNameFlow: Flow<Boolean>
        get() = settingsRepository.directorySortByLastNameFlow

    fun onThemeSettingClicked() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val radioItems = DisplayThemeType.values().map { RadioDialogDataItem(it, it.getString(application)) }

        _themeRadioDialogData.value = RadioDialogData(
            visible = true,
            title = application.getText(R.string.theme).toString(),
            items = RadioDialogDataItems(radioItems, currentTheme)
        )
    }

    fun setTheme(theme: DisplayThemeType) = viewModelScope.launch {
        settingsRepository.setTheme(theme)
        dismissThemeDialog()
    }

    fun dismissThemeDialog() {
        _themeRadioDialogData.value = RadioDialogData(visible = false)
    }

    fun onLastInstalledVersionCodeClicked() = viewModelScope.launch {
        val currentValue = settingsRepository.getLastInstalledVersionCode()
        _lastInstalledVersionCodeDialogData.value = InputDialogData(true, "Version Code", currentValue.toString())
    }

    fun setLastInstalledVersionCode(value: String) {
        value.toIntOrNull()?.let {
            settingsRepository.setLastInstalledVersionCodeAsync(it)
        }
        dismissSetLastInstalledVersionCodeDialog()
    }

    fun dismissSetLastInstalledVersionCodeDialog() {
        _lastInstalledVersionCodeDialogData.value = InputDialogData(visible = false)
    }

    fun setSortByLastName(checked: Boolean) = viewModelScope.launch {
        settingsRepository.setSortByLastName(checked)
    }
}
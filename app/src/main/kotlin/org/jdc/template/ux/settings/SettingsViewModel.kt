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
import org.jdc.template.ui.compose.RadioDialogData
import org.jdc.template.ui.compose.RadioDialogDataItem
import org.jdc.template.ui.compose.RadioDialogDataItems
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val application: Application,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _radioDialogData = MutableStateFlow(RadioDialogData<DisplayThemeType>())
    val radioDialogData: StateFlow<RadioDialogData<DisplayThemeType>> = _radioDialogData

    val currentThemeTitleFlow: Flow<String>
        get() = settingsRepository.themeFlow.map { theme ->
            theme.getString(application)
        }

    val sortByLastNameFlow: Flow<Boolean>
        get() = settingsRepository.directorySortByLastNameFlow

    fun onThemeSettingClicked() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val radioItems = DisplayThemeType.values().map { RadioDialogDataItem(it, it.getString(application)) }

        _radioDialogData.value = RadioDialogData(
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
        _radioDialogData.value = RadioDialogData(visible = false)
    }

    fun setSortByLastName(checked: Boolean) = viewModelScope.launch {
        settingsRepository.setSortByLastName(checked)
    }
}
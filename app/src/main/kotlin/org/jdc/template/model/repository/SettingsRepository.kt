package org.jdc.template.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.datastore.DevicePreferenceDataSource
import org.jdc.template.model.datastore.UserPreferenceDataSource
import org.jdc.template.util.coroutine.ProcessScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository
@Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val devicePreferenceDataSource: DevicePreferenceDataSource,
) {
    val themeFlow: Flow<DisplayThemeType> get() = devicePreferenceDataSource.themePref.flow
    fun setThemeAsync(theme: DisplayThemeType) = ProcessScope.launch { devicePreferenceDataSource.themePref.setValue(theme) }

    val directorySortByLastNameFlow: Flow<Boolean> get() = userPreferenceDataSource.directorySortByLastNamePref.flow
    fun setSortByLastNameAsync(sortAscending: Boolean) = ProcessScope.launch { userPreferenceDataSource.directorySortByLastNamePref.setValue(sortAscending) }

    val isDeveloperModeEnabledFlow: Flow<Boolean> = devicePreferenceDataSource.developerModePref.flow
    suspend fun isDeveloperModeEnabled(): Boolean = devicePreferenceDataSource.developerModePref.flow.first()
    fun setDeveloperMode(enabled: Boolean) = ProcessScope.launch { devicePreferenceDataSource.developerModePref.setValue(enabled) }

    suspend fun getAppInstanceId(): String = devicePreferenceDataSource.appInstanceIdPref.flow.first()

    val lastInstalledVersionCodeFlow: Flow<Int> = devicePreferenceDataSource.lastInstalledVersionCodePref.flow
    suspend fun getLastInstalledVersionCode(): Int = devicePreferenceDataSource.lastInstalledVersionCodePref.flow.first()
    fun setLastInstalledVersionCodeAsync(version: Int) = ProcessScope.launch { devicePreferenceDataSource.lastInstalledVersionCodePref.setValue(version) }

    suspend fun getWorkSchedulerVersion(): Int = devicePreferenceDataSource.workSchedulerVersionPref.flow.first()
    fun setWorkSchedulerVersionAsync(version: Int) = ProcessScope.launch { devicePreferenceDataSource.workSchedulerVersionPref.setValue(version) }
}

package org.jdc.template.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.datastore.DevicePreferenceDataSource
import org.jdc.template.model.datastore.UserPreferenceDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository
@Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val devicePreferenceDataSource: DevicePreferenceDataSource
) {
    val themeFlow: Flow<DisplayThemeType> get() = devicePreferenceDataSource.themeFlow
    suspend fun setTheme(theme: DisplayThemeType) {
        devicePreferenceDataSource.setTheme(theme)
    }

    val directorySortByLastNameFlow: Flow<Boolean> get() = userPreferenceDataSource.directorySortByLastNameFlow
    suspend fun setSortByLastName(sortAscending: Boolean) {
        userPreferenceDataSource.setDirectorySort(sortAscending)
    }

    suspend fun isDeveloperModeEnabled(): Boolean = devicePreferenceDataSource.developerModeFlow.first()
    suspend fun setDeveloperMode(enabled: Boolean) {
        devicePreferenceDataSource.setDeveloperMode(enabled)
    }

    suspend fun getAppInstanceId(): String = devicePreferenceDataSource.appInstanceIdFlow.first()

    suspend fun getLastInstalledVersionCode(): Int = devicePreferenceDataSource.lastInstalledVersionCodeFlow.first()
    suspend fun setLastInstalledVersionCode(version: Int) {
        devicePreferenceDataSource.setLastInstalledVersionCode(version)
    }

    suspend fun getWorkSchedulerVersion(): Int = devicePreferenceDataSource.workSchedulerVersionFlow.first()
    suspend fun setWorkSchedulerVersion(version: Int) {
        devicePreferenceDataSource.setWorkSchedulerVersion(version)
    }

}

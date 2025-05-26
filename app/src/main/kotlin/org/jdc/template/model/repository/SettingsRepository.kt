package org.jdc.template.model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jdc.template.inject.ApplicationScope
import org.jdc.template.model.datastore.DevicePreferenceDataSource
import org.jdc.template.model.datastore.UserPreferenceDataSource
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository
@Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val devicePreferenceDataSource: DevicePreferenceDataSource,
    @ApplicationScope private val appScope: CoroutineScope
) {
    val themeFlow: Flow<DisplayThemeType> get() = devicePreferenceDataSource.themePref.flow
    fun setThemeAsync(theme: DisplayThemeType) = appScope.launch { devicePreferenceDataSource.themePref.setValue(theme) }

    val dynamicThemeFlow: Flow<Boolean> get() = devicePreferenceDataSource.dynamicThemePref.flow
    fun setDynamicThemeAsync(enabled: Boolean) = appScope.launch { devicePreferenceDataSource.dynamicThemePref.setValue(enabled) }

    val directorySortByLastNameFlow: Flow<Boolean> get() = userPreferenceDataSource.directorySortByLastNamePref.flow
    fun setSortByLastNameAsync(sortAscending: Boolean) = appScope.launch { userPreferenceDataSource.directorySortByLastNamePref.setValue(sortAscending) }

    val isDeveloperModeEnabledFlow: Flow<Boolean> get() = devicePreferenceDataSource.developerModePref.flow
    suspend fun isDeveloperModeEnabled(): Boolean = devicePreferenceDataSource.developerModePref.flow.first()
    fun setDeveloperMode(enabled: Boolean) = appScope.launch { devicePreferenceDataSource.developerModePref.setValue(enabled) }

    suspend fun getAppInstanceId(): String = devicePreferenceDataSource.appInstanceIdPref.flow.first()

    val lastInstalledVersionCodeFlow: Flow<Int> get()  = devicePreferenceDataSource.lastInstalledVersionCodePref.flow
    suspend fun getLastInstalledVersionCode(): Int = devicePreferenceDataSource.lastInstalledVersionCodePref.flow.first()
    fun setLastInstalledVersionCodeAsync(version: Int) = appScope.launch { devicePreferenceDataSource.lastInstalledVersionCodePref.setValue(version) }

    val rangeFlow: Flow<Int> get()  = devicePreferenceDataSource.rangePref.flow
    suspend fun getRange(): Int = devicePreferenceDataSource.rangePref.flow.first()
    fun setRangeAsync(range: Int) = appScope.launch { devicePreferenceDataSource.rangePref.setValue(range) }

    suspend fun getWorkSchedulerVersion(): Int = devicePreferenceDataSource.workSchedulerVersionPref.flow.first()
    fun setWorkSchedulerVersionAsync(version: Int) = appScope.launch { devicePreferenceDataSource.workSchedulerVersionPref.setValue(version) }

    companion object {
        val RANGE_OPTIONS = listOf(1, 2, 3)
    }
}

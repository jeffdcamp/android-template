package org.jdc.template.model.datastore

import android.app.Application
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jdc.template.datastore.PreferenceMigrations
import org.jdc.template.model.datastore.migration.DevicePreferenceMigration1To3
import org.jdc.template.model.datastore.migration.DevicePreferenceMigration2
import org.jdc.template.model.datastore.migration.DevicePreferenceMigration3
import org.jdc.template.model.datastore.migration.SharedPreferenceMigration
import org.jdc.template.model.prefs.DisplayThemeType
import org.jdc.template.util.enumValueOfOrDefault
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DevicePreferenceDataSource
@Inject constructor(
    application: Application
) {
    private val version = 3

    private val dataStore: DataStore<Preferences> = application.createDataStore(
        name = "device",
        migrations = listOf(
            PreferenceMigrations.sharedPreferenceMigration(application, toVersion = 1, migrate = { sharedPrefs, currentData ->
                SharedPreferenceMigration.migrateSharedPreferences(sharedPrefs, currentData)
            }),
            PreferenceMigrations(version, listOf(DevicePreferenceMigration2, DevicePreferenceMigration1To3, DevicePreferenceMigration3))
        )
    )

    val themeFlow: Flow<DisplayThemeType> = dataStore.data.map { preferences -> enumValueOfOrDefault(preferences[Keys.THEME], getThemeDefault()) }
    suspend fun setTheme(theme: DisplayThemeType) {
        dataStore.edit { preferences -> preferences[Keys.THEME] = theme.toString() }
    }

    val lastInstalledVersionCodeFlow: Flow<Int> = dataStore.data.map { preferences -> preferences[Keys.LAST_INSTALLED_VERSION_CODE] ?: 0 }
    suspend fun setLastInstalledVersionCode(versionCode: Int) {
        dataStore.edit { preferences -> preferences[Keys.LAST_INSTALLED_VERSION_CODE] = versionCode }
    }

    val workSchedulerVersionFlow: Flow<Int> = dataStore.data.map { preferences -> preferences[Keys.WORK_SCHEDULER_VERSION] ?: 0 }
    suspend fun setWorkSchedulerVersion(version: Int) {
        dataStore.edit { preferences -> preferences[Keys.WORK_SCHEDULER_VERSION] = version }
    }

    val developerModeFlow: Flow<Boolean> = dataStore.data.map { preferences -> preferences[Keys.DEV_MODE] ?: false }
    suspend fun setDeveloperMode(enabled: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.DEV_MODE] = enabled }
    }

    val appInstanceIdFlow: Flow<String> = dataStore.data.map { preferences -> preferences[Keys.APP_INSTANCE_ID] ?: UUID.randomUUID().toString() }

    val appInfoFlow: Flow<AppInfo> = dataStore.data.map { preferences ->
        AppInfo(
            preferences[Keys.DEV_MODE] ?: false,
            preferences[Keys.APP_INSTANCE_ID] ?: UUID.randomUUID().toString(),
            preferences[Keys.WORK_SCHEDULER_VERSION] ?: 0,
            preferences[Keys.LAST_INSTALLED_VERSION_CODE] ?: 0
        )
    }

    suspend fun setAppInfoFlow(appInfo: AppInfo) {
        dataStore.edit { preferences ->
            preferences[Keys.DEV_MODE] = appInfo.devMode
            preferences[Keys.APP_INSTANCE_ID] = appInfo.appInstanceId
            preferences[Keys.WORK_SCHEDULER_VERSION] = appInfo.workerVersion
            preferences[Keys.LAST_INSTALLED_VERSION_CODE] = appInfo.lastInstallVersionCode
        }
    }

    private fun getThemeDefault(): DisplayThemeType {
        return if (Build.VERSION.SDK_INT > 28) {
            // support Android Q System Theme
            DisplayThemeType.SYSTEM_DEFAULT
        } else {
            DisplayThemeType.LIGHT
        }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    object Keys {
        val THEME = preferencesKey<String>("theme")
        val LAST_INSTALLED_VERSION_CODE = preferencesKey<Int>("lastInstalledVersionCode")
        val WORK_SCHEDULER_VERSION = preferencesKey<Int>("workSchedulerVersion")
        val DEV_MODE = preferencesKey<Boolean>("devMode")
        val APP_INSTANCE_ID = preferencesKey<String>("appInstanceId")
    }
}

data class AppInfo(val devMode: Boolean, val appInstanceId: String, val workerVersion: Int, val lastInstallVersionCode: Int)
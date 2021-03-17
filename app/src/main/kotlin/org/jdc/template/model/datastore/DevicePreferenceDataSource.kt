package org.jdc.template.model.datastore

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.datastore.migration.DevicePreferenceMigration1To3
import org.jdc.template.model.datastore.migration.DevicePreferenceMigration2
import org.jdc.template.model.datastore.migration.DevicePreferenceMigration3
import org.jdc.template.model.datastore.migration.SharedPreferenceMigration
import org.jdc.template.util.datastore.PreferenceMigrations
import org.jdc.template.util.ext.enumValueOfOrDefault
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DevicePreferenceDataSource
@Inject constructor(
    private val application: Application
) {
    private val version = 3

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "device",
        produceMigrations = { applicationContext ->
            listOf(
                PreferenceMigrations.sharedPreferenceMigration(applicationContext, toVersion = 1, migrate = { sharedPrefs, currentData ->
                    SharedPreferenceMigration.migrateSharedPreferences(sharedPrefs, currentData)
                }),
                PreferenceMigrations(version, listOf(DevicePreferenceMigration2, DevicePreferenceMigration1To3, DevicePreferenceMigration3))
            )
        },
        corruptionHandler = ReplaceFileCorruptionHandler {
            Timber.e(it, "DevicePreferenceDataSource Corrupted... recreating...")
            emptyPreferences()
        },
    )

    val themeFlow: Flow<DisplayThemeType> = application.dataStore.data.map { preferences -> enumValueOfOrDefault(preferences[Keys.THEME], getThemeDefault()) }
    suspend fun setTheme(theme: DisplayThemeType) {
        application.dataStore.edit { preferences -> preferences[Keys.THEME] = theme.toString() }
    }

    val lastInstalledVersionCodeFlow: Flow<Int> = application.dataStore.data.map { preferences -> preferences[Keys.LAST_INSTALLED_VERSION_CODE] ?: 0 }
    suspend fun setLastInstalledVersionCode(versionCode: Int) {
        application.dataStore.edit { preferences -> preferences[Keys.LAST_INSTALLED_VERSION_CODE] = versionCode }
    }

    val workSchedulerVersionFlow: Flow<Int> = application.dataStore.data.map { preferences -> preferences[Keys.WORK_SCHEDULER_VERSION] ?: 0 }
    suspend fun setWorkSchedulerVersion(version: Int) {
        application.dataStore.edit { preferences -> preferences[Keys.WORK_SCHEDULER_VERSION] = version }
    }

    val developerModeFlow: Flow<Boolean> = application.dataStore.data.map { preferences -> preferences[Keys.DEV_MODE] ?: false }
    suspend fun setDeveloperMode(enabled: Boolean) {
        application.dataStore.edit { preferences -> preferences[Keys.DEV_MODE] = enabled }
    }

    val appInstanceIdFlow: Flow<String> = application.dataStore.data.map { preferences -> preferences[Keys.APP_INSTANCE_ID] ?: UUID.randomUUID().toString() }

    val appInfoFlow: Flow<AppInfo> = application.dataStore.data.map { preferences ->
        AppInfo(
            preferences[Keys.DEV_MODE] ?: false,
            preferences[Keys.APP_INSTANCE_ID] ?: UUID.randomUUID().toString(),
            preferences[Keys.WORK_SCHEDULER_VERSION] ?: 0,
            preferences[Keys.LAST_INSTALLED_VERSION_CODE] ?: 0
        )
    }

    suspend fun setAppInfoFlow(appInfo: AppInfo) {
        application.dataStore.edit { preferences ->
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
        application.dataStore.edit { it.clear() }
    }

    object Keys {
        val THEME = stringPreferencesKey("theme")
        val LAST_INSTALLED_VERSION_CODE = intPreferencesKey("lastInstalledVersionCode")
        val WORK_SCHEDULER_VERSION = intPreferencesKey("workSchedulerVersion")
        val DEV_MODE = booleanPreferencesKey("devMode")
        val APP_INSTANCE_ID = stringPreferencesKey("appInstanceId")
    }
}

data class AppInfo(val devMode: Boolean, val appInstanceId: String, val workerVersion: Int, val lastInstallVersionCode: Int)
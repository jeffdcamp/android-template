package org.jdc.template.shared.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import co.touchlab.kermit.Logger
import okio.Path.Companion.toPath
import org.jdc.template.shared.model.datastore.migration.DevicePreferenceMigration2
import org.jdc.template.shared.model.datastore.migration.DevicePreferenceMigration3
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.shared.util.datastore.DatastorePrefItem
import org.jdc.template.shared.util.datastore.PreferenceMigrations
import org.jdc.template.shared.util.ext.enumValueOfOrDefault
import kotlin.uuid.Uuid

class DevicePreferenceDataSource (
    deviceDataStore: DeviceDataStore
) {
    private val dataStore: DataStore<Preferences> = deviceDataStore.datastore

    val themePref: DatastorePrefItem<DisplayThemeType> = DatastorePrefItem.createEnum(dataStore, Keys.THEME) { enumValueOfOrDefault(it, DisplayThemeType.SYSTEM_DEFAULT) }
    val dynamicThemePref: DatastorePrefItem<Boolean> = DatastorePrefItem.create(dataStore, Keys.DYNAMIC_THEME, false)
    val lastInstalledVersionCodePref: DatastorePrefItem<Int> = DatastorePrefItem.create(dataStore, Keys.LAST_INSTALLED_VERSION_CODE, 0)
    val rangePref: DatastorePrefItem<Int> = DatastorePrefItem.create(dataStore, Keys.RANGE, 1)
    val workSchedulerVersionPref: DatastorePrefItem<Int> = DatastorePrefItem.create(dataStore, Keys.WORK_SCHEDULER_VERSION, 0)
    val developerModePref: DatastorePrefItem<Boolean> = DatastorePrefItem.create(dataStore, Keys.DEV_MODE, false)
    val appInstanceIdPref: DatastorePrefItem<String> = DatastorePrefItem.create(dataStore, Keys.APP_INSTANCE_ID, Uuid.random().toString())
    val colorsETagPref: DatastorePrefItem<String> = DatastorePrefItem.create(dataStore, Keys.COLORS_ETAG, "0")

    val appInfoPref: DatastorePrefItem<AppInfo> = DatastorePrefItem.createCustom(
        dataStore = dataStore,
        read = { preferences ->
            AppInfo(
                preferences[Keys.DEV_MODE] ?: false,
                preferences[Keys.APP_INSTANCE_ID] ?: Uuid.random().toString(),
                preferences[Keys.WORK_SCHEDULER_VERSION] ?: 0,
                preferences[Keys.LAST_INSTALLED_VERSION_CODE] ?: 0
            )
        },
        write = { preferences, appInfo ->
            preferences[Keys.DEV_MODE] = appInfo.devMode
            preferences[Keys.APP_INSTANCE_ID] = appInfo.appInstanceId
            preferences[Keys.WORK_SCHEDULER_VERSION] = appInfo.workerVersion
            preferences[Keys.LAST_INSTALLED_VERSION_CODE] = appInfo.lastInstallVersionCode
        }
    )

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    object Keys {
        val THEME = stringPreferencesKey("theme")
        val DYNAMIC_THEME = booleanPreferencesKey("dynamicTheme")
        val LAST_INSTALLED_VERSION_CODE = intPreferencesKey("lastInstalledVersionCode")
        val RANGE = intPreferencesKey("range")
        val WORK_SCHEDULER_VERSION = intPreferencesKey("workSchedulerVersion")
        val DEV_MODE = booleanPreferencesKey("devMode")
        val APP_INSTANCE_ID = stringPreferencesKey("appInstanceId")
        val COLORS_ETAG = stringPreferencesKey("colorsETag")
    }

    companion object {
        private const val VERSION = 3

        fun createDataStore(producePath: () -> String): DataStore<Preferences> {
            return PreferenceDataStoreFactory.createWithPath(
                migrations = listOf(
                    PreferenceMigrations(VERSION, listOf(DevicePreferenceMigration2, DevicePreferenceMigration3))
                ),
                produceFile = { producePath().toPath() },
                corruptionHandler = ReplaceFileCorruptionHandler {
                    Logger.e(it) { "DevicePreferenceDataSource Corrupted... recreating..." }
                    emptyPreferences()
                }
            )
        }
    }
}

data class AppInfo(val devMode: Boolean, val appInstanceId: String, val workerVersion: Int, val lastInstallVersionCode: Int)

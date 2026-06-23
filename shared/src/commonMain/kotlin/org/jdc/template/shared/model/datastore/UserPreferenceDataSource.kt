package org.jdc.template.shared.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import co.touchlab.kermit.Logger
import okio.Path
import org.jdc.template.shared.util.datastore.DatastorePrefItem
import org.jdc.template.shared.util.datastore.PreferenceMigrations

class UserPreferenceDataSource(
    userDataStore: UserDataStore
) {
    private val dataStore: DataStore<Preferences> = userDataStore.datastore

    val directorySortByLastNamePref: DatastorePrefItem<Boolean> = DatastorePrefItem.create(dataStore, Keys.DIRECTORY_SORT, true)

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    private object Keys {
        val DIRECTORY_SORT = booleanPreferencesKey("directorySort")
    }

    companion object {
        const val NAME = "user"
        private const val VERSION = 1

        fun createDataStore(producePath: () -> Path): DataStore<Preferences> {
            return PreferenceDataStoreFactory.createWithPath(
                migrations = listOf(
                    PreferenceMigrations(VERSION, emptyList())

                ),
                produceFile = { producePath() },
                corruptionHandler = ReplaceFileCorruptionHandler {
                    Logger.e(it) { "UserPreferenceDataSource Corrupted... recreating..." }
                    emptyPreferences()
                }
            )
        }
    }
}

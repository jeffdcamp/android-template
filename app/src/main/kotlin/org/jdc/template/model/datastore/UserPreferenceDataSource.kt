package org.jdc.template.model.datastore

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceDataSource
@Inject constructor(
    application: Application
) {
    private val dataStore: DataStore<Preferences> = application.createDataStore(
        name = "user"
    )

    val directorySortByLastNameFlow: Flow<Boolean> = dataStore.data.map { preferences -> preferences[Keys.DIRECTORY_SORT] ?: true }
    suspend fun setDirectorySort(sortAscending: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.DIRECTORY_SORT] = sortAscending }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    private object Keys {
        val DIRECTORY_SORT = booleanPreferencesKey("directorySort")
    }
}
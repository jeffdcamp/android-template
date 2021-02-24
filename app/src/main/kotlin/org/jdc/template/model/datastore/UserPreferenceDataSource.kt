package org.jdc.template.model.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceDataSource
@Inject constructor(
    private val application: Application
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    val directorySortByLastNameFlow: Flow<Boolean> = application.dataStore.data.map { preferences -> preferences[Keys.DIRECTORY_SORT] ?: true }
    suspend fun setDirectorySort(sortAscending: Boolean) {
        application.dataStore.edit { preferences -> preferences[Keys.DIRECTORY_SORT] = sortAscending }
    }

    suspend fun clearAll() {
        application.dataStore.edit { it.clear() }
    }

    private object Keys {
        val DIRECTORY_SORT = booleanPreferencesKey("directorySort")
    }
}
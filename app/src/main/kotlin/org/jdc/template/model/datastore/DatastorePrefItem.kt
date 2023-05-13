package org.jdc.template.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jdc.template.util.ext.mapDistinct

interface DatastorePrefItem<T: Any?> {
    val flow: Flow<T>
    suspend fun setValue(value: T)

    companion object {
        fun <T: Any?> create(dataStore: DataStore<Preferences>, preferenceKey: Preferences.Key<T>, defaultValue: T): DatastorePrefItem<T> {
            return DefaultDatastorePrefItem(dataStore, preferenceKey, defaultValue)
        }

        fun <T : Enum<T>> createEnum(dataStore: DataStore<Preferences>, preferenceKey: Preferences.Key<String>, preferenceKeyToEnum: (String?) -> T): DatastorePrefItem<T> {
            return DatastorePrefEnumItem(dataStore, preferenceKey, preferenceKeyToEnum)
        }

        fun <PREF_T, VALUE_CLASS_T: Any?> createValueClass(
            dataStore: DataStore<Preferences>,
            preferenceKey: Preferences.Key<PREF_T>,
            prefValueToValueClass: (PREF_T) -> VALUE_CLASS_T,
            valueClassToPrefValue: (VALUE_CLASS_T) -> PREF_T?,
            defaultValue: VALUE_CLASS_T
        ): DatastorePrefItem<VALUE_CLASS_T> {
            return DatastorePrefValueClass(dataStore, preferenceKey, prefValueToValueClass, valueClassToPrefValue, defaultValue)
        }

        fun <T: Any?> createCustom(dataStore: DataStore<Preferences>, read: (Preferences) -> T, write: (MutablePreferences, T) -> Unit): DatastorePrefItem<T> {
            return DatastorePrefCustomItem(dataStore, read, write)
        }
    }
}

class DefaultDatastorePrefItem<T: Any?>(
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<T>,
    private val defaultValue: T
) : DatastorePrefItem<T> {
    override val flow: Flow<T> = dataStore.data.mapDistinct { preferences -> preferences[preferenceKey] ?: defaultValue }
    override suspend fun setValue(value: T) {
        dataStore.edit { preferences -> preferences[preferenceKey] = value }
    }
}

class DatastorePrefValueClass<PREF_T, VALUE_CLASS_T: Any?>(
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<PREF_T>,
    private val prefValueToValueClass: (PREF_T) -> VALUE_CLASS_T,
    private val valueClassToPrefValue: (VALUE_CLASS_T) -> PREF_T?,
    private val defaultValue: VALUE_CLASS_T
) : DatastorePrefItem<VALUE_CLASS_T> {
    override val flow: Flow<VALUE_CLASS_T> = dataStore.data.mapDistinct { preferences -> preferences[preferenceKey]?.let { prefValueToValueClass(it) } ?: defaultValue }
    override suspend fun setValue(value: VALUE_CLASS_T) {
        dataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(preferenceKey)
            } else {
                valueClassToPrefValue(value)?.let { preferences[preferenceKey] = it }
            }
        }
    }
}

class DatastorePrefEnumItem<T : Enum<T>>(
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<String>,
    val preferenceKeyToEnum: (String?) -> T
) : DatastorePrefItem<T> {
    override val flow: Flow<T> = dataStore.data.mapDistinct { preferences -> preferenceKeyToEnum(preferences[preferenceKey]) }
    override suspend fun setValue(value: T) {
        dataStore.edit { preferences -> preferences[preferenceKey] = value.toString() }
    }
}

class DatastorePrefCustomItem<T: Any?>(
    private val dataStore: DataStore<Preferences>,
    val read: (Preferences) -> T,
    val write: (MutablePreferences, T) -> Unit
) : DatastorePrefItem<T> {
    override val flow: Flow<T> = dataStore.data.map { preferences ->
        read(preferences)
    }
    override suspend fun setValue(value: T) {
        dataStore.edit { preferences ->
            write(preferences, value)
        }
    }
}

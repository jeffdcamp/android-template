@file:Suppress("unused")

package org.jdc.template.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.toMutablePreferences
import androidx.datastore.preferences.core.toPreferences

class DestructivePreferenceMigration(private val toVersion: Int) : DataMigration<Preferences> {
    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        return (currentData[PREFERENCES_VERSION_KEY] ?: 0) != toVersion
    }
    
    override suspend fun migrate(currentData: Preferences): Preferences {
        val mutablePreferences = currentData.toMutablePreferences()
        
        mutablePreferences.clear()
        mutablePreferences[PREFERENCES_VERSION_KEY] = toVersion

        return mutablePreferences.toPreferences()
    }

    override suspend fun cleanUp() {
        // do nothing
    }
}
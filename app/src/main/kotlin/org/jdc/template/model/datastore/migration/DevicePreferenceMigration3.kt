package org.jdc.template.model.datastore.migration

import androidx.datastore.preferences.core.Preferences
import org.jdc.template.util.datastore.PreferenceMigration

object DevicePreferenceMigration3 : PreferenceMigration(2, 3) {
    override suspend fun migrate(currentData: Preferences): Preferences {
        val mutablePreferences = currentData.toMutablePreferences()

        // do preference migrations from version 2 to 3 here

        return mutablePreferences.toPreferences()
    }
}
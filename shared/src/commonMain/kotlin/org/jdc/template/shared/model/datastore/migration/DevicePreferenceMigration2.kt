package org.jdc.template.shared.model.datastore.migration

import androidx.datastore.preferences.core.Preferences
import org.jdc.template.shared.util.datastore.PreferenceMigration

object DevicePreferenceMigration2 : PreferenceMigration(1, 2) {
    override suspend fun migrate(currentData: Preferences): Preferences {
        val mutablePreferences = currentData.toMutablePreferences()

        // do preference migrations from version 1 to 2 here

        return mutablePreferences.toPreferences()
    }
}
package org.jdc.template.shared.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

object DatastoreUtil {
    const val USER = "user"
    const val DEVICE = "device"

    fun createDataStoreFilename(name: String): String = "$name.preferences_pb"
}

class UserDataStore(val datastore: DataStore<Preferences>)
class DeviceDataStore(val datastore: DataStore<Preferences>)
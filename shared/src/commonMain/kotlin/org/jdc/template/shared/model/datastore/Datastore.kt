package org.jdc.template.shared.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

object DatastoreUtil {
    const val USER = "user"
    const val DEVICE = "device"
}

class UserDataStore(val datastore: DataStore<Preferences>)
class DeviceDataStore(val datastore: DataStore<Preferences>)
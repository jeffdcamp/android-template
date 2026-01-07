package org.jdc.template.shared.util.file

import okio.Path
import okio.Path.Companion.toPath

actual object AppFileSystem {
    private val APP_DATA_DIR = "appData".toPath()
    private val EXTERNAL_APP_DATA_DIR = "externalAppData".toPath()

    actual fun getFilesDir(): Path = APP_DATA_DIR / "files"

    actual fun getDatabasesPath(name: String): Path = APP_DATA_DIR / "databases/$name"

    actual fun getDatastoreDir(): Path = APP_DATA_DIR / "datastore"
    actual fun getDatastorePath(name: String): Path = APP_DATA_DIR / "datastore/$name.preferences_pb"

    actual fun getExternalFilesDir(type: String?): Path? = EXTERNAL_APP_DATA_DIR / "files" / "$type".toPath()
}
package org.jdc.template.shared.util.file

import okio.Path

expect object AppFileSystem {
    fun getFilesDir(): Path
    fun getDatabasesPath(name: String): Path

    fun getDatastoreDir(): Path
    fun getDatastorePath(name: String): Path

    fun getExternalFilesDir(type: String?): Path?
}
package org.jdc.template.shared

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM

object TestFilesystem {
    private val fileSystem = FileSystem.SYSTEM

    val FILESYSTEM_DIR_PATH = "build/test-filesystem".toPath()
    val INTERNAL_DIR_PATH = "$FILESYSTEM_DIR_PATH/internal".toPath()
    val EXTERNAL_DIR_PATH = "$FILESYSTEM_DIR_PATH/external".toPath()

    val INTERNAL_FILES_DIR_PATH = INTERNAL_DIR_PATH / "files"
    val INTERNAL_DATABASES_DIR_PATH: Path = INTERNAL_DIR_PATH / "databases"

    val EXTERNAL_FILES_DIR_PATH = EXTERNAL_DIR_PATH / "files"

    fun deleteFilesystem() {
        fileSystem.delete(FILESYSTEM_DIR_PATH)
    }
}
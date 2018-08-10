package org.jdc.template

import java.io.File

object TestFilesystem {
    const val FILESYSTEM_DIR_PATH = "build/test-filesystem"
    val FILESYSTEM_DIR = File(FILESYSTEM_DIR_PATH)
    const val INTERNAL_DIR_PATH = "$FILESYSTEM_DIR_PATH/internal"
    val INTERNAL_DIR = File(INTERNAL_DIR_PATH)
    const val EXTERNAL_DIR_PATH = "$FILESYSTEM_DIR_PATH/external"
    val EXTERNAL_DIR = File(EXTERNAL_DIR_PATH)

    const val INTERNAL_FILES_DIR_PATH = "$INTERNAL_DIR_PATH/files"
    val INTERNAL_FILES_DIR = File(INTERNAL_FILES_DIR_PATH)
    const val INTERNAL_DATABASES_DIR_PATH = "$INTERNAL_DIR_PATH/databases"

    const val EXTERNAL_FILES_DIR_PATH = "$EXTERNAL_DIR_PATH/files"
    val EXTERNAL_FILES_DIR = File(EXTERNAL_FILES_DIR_PATH)

    fun deleteFilesystem() {
        FILESYSTEM_DIR.deleteRecursively()
    }

    fun copyDatabase(sourcePath: String, targetPath: String) {
        val targetDBFile = File(targetPath)
        val dbDirectory = targetDBFile.parentFile

        try {
            dbDirectory.mkdirs()

            if (targetDBFile.exists()) {
                targetDBFile.delete()
            }

            File(sourcePath).copyTo(targetDBFile, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

package org.jdc.template.shared.util.ext

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.openZip

/**
 * Unzip file from zip file and output to file Path
 * @param sourceZipFile Zip file containing file
 * @param fileInZip file Path of file in zip file
 * @param outFile target file Path to copy the file to
 */
fun FileSystem.unzipFile(sourceZipFile: Path, fileInZip: Path, outFile: Path) {
    require(exists(sourceZipFile)) { "sourceZipFile ($sourceZipFile) does not exist" }
    val zipFilesystem = openZip(sourceZipFile)
    zipFilesystem.copyFileToFileSystem(fileInZip, this, outFile)
}

/**
 * Unzip the full content of a zip file to a target directory
 * @param sourceZipFile Source zip file
 * @param targetDir Directory that will be created and contents will be unzipped to
 * @param mustCreate Fail if the directory already exists. default = false
 */
fun FileSystem.unzip(sourceZipFile: Path, targetDir: Path, mustCreate: Boolean = false) {
    require(!isDirectory(sourceZipFile)) { "sourceZipFile must be a file" }
    require(exists(sourceZipFile)) { "sourceZipFile ($sourceZipFile) does not exist" }

    if (exists(targetDir) && !mustCreate) {
        require(isDirectory(targetDir)) { "existing targetDir ($targetDir) is not a directory" }
    }

    // create targetDir
    createDirectories(targetDir, mustCreate = mustCreate)
    require(exists(targetDir)) { "targetDir could not be created" }
    val targetDirFull = canonicalize(targetDir)

    val zipFilesystem = openZip(sourceZipFile)
    zipFilesystem.listRecursively("".toPath()).forEach { path ->
        val pathName = path.toString().removePrefix("/")
        if (zipFilesystem.isDirectory(path)) {
            createDirectories(targetDirFull / pathName)
        } else {
            zipFilesystem.copyFileToFileSystem(path, this,targetDirFull / pathName)
        }
    }
}

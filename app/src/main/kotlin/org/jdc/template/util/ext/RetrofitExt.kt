package org.jdc.template.util.ext

import okhttp3.ResponseBody
import okio.FileSystem
import okio.Path
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

/**
 * Write Retrofit Response stream to file
 *
 * @param fileSystem Filesystem to save the file outputFile to
 * @param outputFile target file.  If the file already exists, then remove the file
 * @return true if the file streamed successfully to file and the new file exists
 */
fun Response<ResponseBody>.saveBodyToFile(fileSystem: FileSystem, outputFile: Path): Boolean {
    Timber.d("Saving response [${raw().request.url}] to file [$outputFile]...")
    var success = false
    try {
        // if the target file already exists, remove it.
        if (fileSystem.exists(outputFile)) {
            fileSystem.delete(outputFile)
            if (fileSystem.exists(outputFile)) {
                Timber.e("Failed to save stream to [${fileSystem.canonicalize(outputFile)}]... file already exists and cannot be deleted")
                return false
            }
        }

        // write stream to file
        success = body()?.saveToFile(fileSystem, outputFile) ?: false
    } catch (e: IOException) {
        Timber.e(e, "Failed to save response stream to [%s]", outputFile.name)

        try {
            if (fileSystem.exists(outputFile)) {
                fileSystem.delete(outputFile)
            }
        } catch (ignore: Exception) {
            // delete quietly
        }
    }
    return success
}

/**
 * Write Retrofit ResponseBody to file
 *
 * @param fileSystem Filesystem to save the file outputFile to
 * @param outputFile target file.  If the file already exists, then remove the file
 * @return true if the file streamed successfully to file and the new file exists
 */
fun ResponseBody.saveToFile(fileSystem: FileSystem, outputFile: Path): Boolean {
    // write stream to file
    byteStream().use { input ->
        fileSystem.copyInputStreamToFile(input, outputFile)
    }



    // check to see if the file exists
    return fileSystem.exists(outputFile)
}
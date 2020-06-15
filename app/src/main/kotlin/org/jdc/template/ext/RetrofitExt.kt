package org.jdc.template.ext

import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.io.IOException

/**
 * Write Retrofit Response stream to file
 *
 * @param outputFile target file.  If the file already exists, then remove the file
 * @return true if the file streamed successfully to file and the new file exists
 */
fun Response<ResponseBody>.saveBodyToFile(outputFile: File): Boolean {
    Timber.d("Saving response [${raw().request.url}] to file [${outputFile.absolutePath}]...")
    var success = false
    try {
        // if the target file already exists, remove it.
        if (outputFile.exists() && !outputFile.delete()) {
            Timber.e("Failed to save stream to [${outputFile.absoluteFile}]... file already exists and cannot be deleted")
            return false
        }

        // write stream to file
        success = body()?.saveToFile(outputFile) ?: false
    } catch (e: IOException) {
        Timber.e(e, "Failed to save response stream to [%s]", outputFile.name)

        try {
            if (outputFile.exists()) {
                outputFile.delete()
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
 * @param outputFile target file.  If the file already exists, then remove the file
 * @return true if the file streamed successfully to file and the new file exists
 */
fun ResponseBody.saveToFile(outputFile: File): Boolean {
    // write stream to file
    byteStream().use { input ->
        outputFile.outputStream().use { fileOut ->
            input.copyTo(fileOut)
            fileOut.flush()
        }
    }

    // check to see if the file exists
    return outputFile.exists()
}
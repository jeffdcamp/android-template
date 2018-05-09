package org.jdc.template.ext

import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.File

fun Response<ResponseBody>.saveBodyToFile(outputFile: File): Boolean {
    Timber.d("Saving response [${raw().request().url().url()}] to file [${outputFile.absolutePath}]...")
    body()?.let {
        outputFile.copyInputStreamToFile(it.byteStream())
    }
    return outputFile.exists()
}
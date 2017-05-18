package org.jdc.template.util

import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebServiceUtil
@Inject
constructor() {
    fun saveResponseToFile(response: Response<ResponseBody>, outputFile: File): Boolean {
        Timber.d("Saving response [${response.raw().request().url().url()}] to file [${outputFile.absolutePath}]...")
        response.body()?.let {
            outputFile.copyInputStreamToFile(it.byteStream())
        }
        return outputFile.exists()
    }
}

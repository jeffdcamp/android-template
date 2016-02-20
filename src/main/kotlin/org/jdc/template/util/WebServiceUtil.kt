package org.jdc.template.util

import android.util.Log
import okhttp3.ResponseBody
import org.jdc.template.App
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebServiceUtil

@Inject
constructor() {
    fun saveResponseToFile(response: Response<ResponseBody>, outputFile: File): Boolean {
        Log.d(TAG, "Saving response [" + response.raw().request().url().url().toString() + "] to file [" + outputFile.absolutePath + "]...")
        outputFile.copyInputStreamToFile(response.body().byteStream())
        return outputFile.exists()
    }

    companion object {
        private val TAG = App.createTag(WebServiceUtil::class.java)
    }
}

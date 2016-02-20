package org.jdc.template.util

import android.util.Log
import okhttp3.ResponseBody
import org.apache.commons.io.FileUtils
import org.jdc.template.App
import retrofit2.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebServiceUtil

@Inject
constructor() {
    fun saveResponseToFile(response: Response<ResponseBody>, outputFile: File): Boolean {
        Log.d(TAG, "Saving response [" + response.raw().request().url().url().toString() + "] to file [" + outputFile.absolutePath + "]...")
        var success = false
        try {
            FileUtils.copyInputStreamToFile(response.body().byteStream(), outputFile) // Closes all streams
            success = outputFile.exists()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to save webservice stream to [" + outputFile.name + "] error: " + e.message, e)

            if (outputFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete() // NOSONAR - just delete if we can but we don't need to check the return value
            }
        }

        return success
    }

    companion object {
        private val TAG = App.createTag(WebServiceUtil::class.java)
    }
}

package org.jdc.template.work

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

/**
 * Example simple worker... one that should execute every time it is called
 */
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    @WorkerThread
    override fun doWork(): Result {
        val inputText = inputData.getString(KEY_TEXT)

        logProgress("RUNNING: Text: [$inputText]")
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            Timber.e("Sleep Failure")
        }

        // return result
        return Result.SUCCESS
    }

    private fun logProgress(progress: String) {
        Timber.e("*** SyncWorker[$progress] Thread:[${Thread.currentThread().name}]  Job:[${this.id}]")
    }

    companion object {
        private const val KEY_TEXT = "TEXT"

        fun createInputData(
            text: String
        ): Data {
            val dataBuilder = Data.Builder()
                .putString(KEY_TEXT, text)

            return dataBuilder.build()
        }
    }
}

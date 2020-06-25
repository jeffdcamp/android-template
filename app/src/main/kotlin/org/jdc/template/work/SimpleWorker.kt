package org.jdc.template.work

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * Example simple worker... one that should execute every time it is called
 */
class SimpleWorker
@WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    @WorkerThread
    override suspend fun doWork(): Result {
        val inputText = inputData.getString(KEY_TEXT)

        logProgress("RUNNING: Text: [$inputText]")
        try {
            delay(1000)
        } catch (e: InterruptedException) {
            Timber.e("Sleep Failure")
        }

        // return result
        return Result.success()
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

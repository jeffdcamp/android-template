package org.jdc.template.work

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import co.touchlab.kermit.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

/**
 * Example simple worker... one that should execute every time it is called
 */
@HiltWorker
class SimpleWorker
@AssistedInject constructor(
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
            Logger.e(e) { "Sleep Failure" }
        }

        // return result
        return Result.success()
    }

    private fun logProgress(progress: String) {
        Logger.e { "*** SyncWorker[$progress] Thread:[${Thread.currentThread().name}]  Job:[${this.id}]" }
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

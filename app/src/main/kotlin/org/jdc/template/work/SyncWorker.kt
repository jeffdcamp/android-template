package org.jdc.template.work

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.worker.WorkerInject
import org.jdc.template.prefs.Prefs
import timber.log.Timber

/**
 * Example data sync worker... one that should sync your changes when the user is finished changing/editing data
 *
 * This type of worker should:
 * - Only run once (don't sync on every single edit)
 * - Delay for 30 seconds (group together as many changes/edits as possible)
 * - Replace any existing scheduled (if there is a pending sync request... remove it and reset delay for 30 seconds)
 * - Require network connection
 */
class SyncWorker
@WorkerInject constructor(
        val prefs: Prefs,
        @Assisted appContext: Context,
        @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    @WorkerThread
    override suspend fun doWork(): Result {
        logProgress("RUNNING")

        // simulate some work...
        logProgress("WORK1-STARTED")
        logProgress("WORK1-developerMode=${prefs.developerMode}")
        Thread.sleep(5000)
        logProgress("WORK1-FINISHED")

        if (isStopped) {
            logProgress("WORK2-SKIPPED")
            return Result.success()
        }

        // simulate some work...
        logProgress("WORK2-STARTED")
        Thread.sleep(1000)
        logProgress("WORK2-FINISHED")

        logProgress("FINISHED")
        // return result
        return Result.success()
    }

    private fun logProgress(progress: String) {
        Timber.e("*** SyncWorker[$progress] Thread:[${Thread.currentThread().name}]  Job:[${this.id}]")
    }

    companion object {
        const val UNIQUE_WORK_NAME = "SyncWorker"
    }
}

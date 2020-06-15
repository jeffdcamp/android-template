package org.jdc.template.work


import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkScheduler
@Inject constructor(
    context: Application
) {
    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun scheduleSimpleWork(text: String) {
        val inputData = SimpleWorker.createInputData(text)

        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
            .setConstraints(workerConstraints)
            .setInputData(inputData)
            .build()

        workManager.enqueue(workRequest)
    }

    fun scheduleSync(now: Boolean = false) {
        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequestBuilder = OneTimeWorkRequest.Builder(SyncWorker::class.java)
            .setConstraints(workerConstraints)

        if (!now) {
            @Suppress("MagicNumber") // explicit values in function
            workRequestBuilder.setInitialDelay(10, TimeUnit.SECONDS)
        }

        val workRequest = workRequestBuilder.build()

        workManager.beginUniqueWork(SyncWorker.UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest)
            .enqueue()
    }
}

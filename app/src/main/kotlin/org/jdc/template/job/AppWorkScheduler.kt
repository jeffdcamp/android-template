package org.jdc.template.job


import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppWorkScheduler
@Inject constructor(
    private val workManager: WorkManager
) {
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
            workRequestBuilder.setInitialDelay(10, TimeUnit.SECONDS)
        }

        val workRequest = workRequestBuilder.build()

        workManager.beginUniqueWork(SyncWorker.UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest)
            .enqueue()
    }
}

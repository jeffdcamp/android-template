package org.jdc.template.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.jdc.template.BuildConfig
import org.jdc.template.coroutine.ProcessScope
import org.jdc.template.model.repository.SettingsRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("MemberVisibilityCanBePrivate")
@Singleton
class WorkScheduler
@Inject constructor(
    @ApplicationContext context: Context,
    private val settingsRepository: SettingsRepository
) {
    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun startPeriodicWorkSchedules() = ProcessScope.launch {
        // cancel all work if the version changed
        if (settingsRepository.getWorkSchedulerVersion() != WORK_SCHEDULER_VERSION) {
            workManager.cancelAllWork()
            settingsRepository.setWorkSchedulerVersion(WORK_SCHEDULER_VERSION)
        }

        @Suppress("ConstantConditionIf")
        // don't schedule work for debug builds because connection to Firebase may not work (default signing will be wrong for debug builds)
        if (BuildConfig.BUILD_TYPE != "debug") {
            scheduleRemoteConfigUpdate()
        }
    }

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

    fun scheduleRemoteConfigUpdate() {
        val workRequest = createStandardPeriodicWorkRequest<RemoteConfigSyncWorker>(5, TimeUnit.DAYS)
        workManager.enqueueUniquePeriodicWork(RemoteConfigSyncWorker.UNIQUE_PERIODIC_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    /**
     * Create a standard PERIODIC request that...
     * - Requires a Network Connection
     */
    private inline fun <reified T : CoroutineWorker> createStandardPeriodicWorkRequest(
        repeatInterval: Long,
        timeUnit: TimeUnit,
        inputDataBuilder: Data.Builder = Data.Builder(),
        initialDelaySeconds: Long = -1
    ): PeriodicWorkRequest {
        val workerConstraintsBuilder = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            workerConstraintsBuilder.setRequiresDeviceIdle(true)
//        }

        val workerConstraints = workerConstraintsBuilder.build()

        val workRequestBuilder = PeriodicWorkRequestBuilder<T>(repeatInterval, timeUnit, 3, TimeUnit.HOURS)

        workRequestBuilder.setConstraints(workerConstraints)
        workRequestBuilder.setInputData(inputDataBuilder.build())

        if (initialDelaySeconds != -1L) {
            workRequestBuilder.setInitialDelay(initialDelaySeconds, TimeUnit.SECONDS)
        }

        return workRequestBuilder.build()
    }

    companion object {
        const val WORK_SCHEDULER_VERSION = 2 // changing this version will force a cancel of all work and reschedule
    }
}

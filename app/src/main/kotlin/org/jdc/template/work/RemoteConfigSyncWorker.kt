package org.jdc.template.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.touchlab.kermit.Logger
import org.jdc.template.model.config.RemoteConfig

class RemoteConfigSyncWorker(
    private val remoteConfig: RemoteConfig,
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Logger.i { "RemoteConfigSyncWorker: fetching and activating" }
        remoteConfig.fetchAndActivateNow()
        return Result.success()
    }

    companion object {
        const val UNIQUE_ONE_TIME_WORK_NAME = "OneTimeRemoteConfigSyncWorker"
        const val UNIQUE_PERIODIC_WORK_NAME = "PeriodicRemoteConfigSyncWorker"
    }
}

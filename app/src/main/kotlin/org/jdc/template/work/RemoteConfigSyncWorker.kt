package org.jdc.template.work

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.jdc.template.model.remoteconfig.BaseFirebaseRemoteConfig.Companion.DEFAULT_TIMEOUT_FETCH_SECONDS_LONG
import org.jdc.template.model.remoteconfig.RemoteConfig
import timber.log.Timber

class RemoteConfigSyncWorker
@WorkerInject constructor(
    private val remoteConfig: RemoteConfig,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.i("RemoteConfigSyncWorker: fetching and activating")
        remoteConfig.fetchAndActivateNow(DEFAULT_TIMEOUT_FETCH_SECONDS_LONG)
        return Result.success()
    }

    companion object {
        const val UNIQUE_ONE_TIME_WORK_NAME = "OneTimeRemoteConfigSyncWorker"
        const val UNIQUE_PERIODIC_WORK_NAME = "PeriodicRemoteConfigSyncWorker"
    }
}

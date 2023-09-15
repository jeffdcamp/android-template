@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package org.jdc.template.model.config

import androidx.annotation.XmlRes
import co.touchlab.kermit.Logger
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.hours

@Suppress("UnnecessaryAbstractClass")
abstract class BaseFirebaseRemoteConfig(@XmlRes remoteConfigDefaults: Int) {

    init {
        // config
        val firebaseSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(getMinimumFetchIntervalInSeconds()) // default is 12 hours... for testing/debug set to 0L
            .setFetchTimeoutInSeconds(getFetchTimeoutInSeconds())
            .build()

        Firebase.remoteConfig.setConfigSettingsAsync(firebaseSettings)

        // set defaults
        Firebase.remoteConfig.setDefaultsAsync(remoteConfigDefaults)
    }

    fun getMinimumFetchIntervalInSeconds(): Long = 12.hours.inWholeSeconds

    fun getFetchTimeoutInSeconds(): Long = DEFAULT_TIMEOUT_FETCH_SECONDS_LONG

    @Suppress("ForbiddenVoid") // forced by Java class
    fun fetch(now: Boolean = false): Task<Void> {
        Logger.d { "RemoteConfig: fetch  now=$now" }

        return if (now) {
            // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
            // LIMIT: 5 calls per hour
            Firebase.remoteConfig.fetch(0L)
        } else {
            // Starts fetching configs, adhering to the default minimum fetch interval.
            Firebase.remoteConfig.fetch()
        }
    }

    fun activate(): Task<Boolean> {
        Logger.d { "RemoteConfig: activate" }
        return Firebase.remoteConfig.activate()
    }

    fun fetchAndActivateAsync(now: Boolean = false, onFailureBlock: () -> Unit = {}, onSuccessBlock: () -> Unit = {}) {
        Logger.d { "RemoteConfig: fetchAndActivateAsync" }

        val fetchTask = if (now) {
            // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
            Firebase.remoteConfig.fetch(0L)
        } else {
            // Starts fetching configs, adhering to the default minimum fetch interval.
            Firebase.remoteConfig.fetch()
        }

        fetchTask.addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    Firebase.remoteConfig.activate()
                    onSuccessBlock()
                }
                else -> {
                    Logger.w { "Failed to sync/fetch RemoteConfig" }
                    onFailureBlock()
                }
            }
        }
    }

    /**
     * Fetch and Activate synchronously.... if there is a timeout issue, then don't error
     * @param timeoutSeconds How long the fetch should be allowed to take before timeout will occur
     * @return true if the fetch was successful and we could apply the changes; false if there was an error fetching and activating
     */
    suspend fun fetchAndActivateNow(timeoutSeconds: Long = DEFAULT_TIMEOUT_FETCH_SECONDS_SHORT): Boolean {
        Logger.d { "RemoteConfig: fetchAndActivateNow" }

        // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
        val fetchTask = Firebase.remoteConfig.fetch(0L)

        // Await fetch, then activate right away if fetch was successful
        try {
            fetchTask.await()
            if (fetchTask.isSuccessful) {
                Firebase.remoteConfig.activate()
                return true
            }
        } catch (expected: Exception) {
            Logger.e(expected) { "Failed to FetchAndActivate" }
        }

        return false
    }

    fun getStatusDetails(): String {
        val info = Firebase.remoteConfig.info
        return "Last Fetch Status: [${getLastFetchStatus()}]  " +
                "Fetch: [${Instant.fromEpochMilliseconds(info.fetchTimeMillis)}]  " +
                "Min Fetch Interval: [${info.configSettings.minimumFetchIntervalInSeconds}s] " +
                "Fetch Timeout: [${info.configSettings.fetchTimeoutInSeconds}s]"
    }

    fun getLastFetchStatus(): String {
        return when (Firebase.remoteConfig.info.lastFetchStatus) {
            FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS -> "Success"
            FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE -> "Failure"
            FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET -> "No Fetch Yet"
            FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED -> "Throttled"
            else -> "Unknown"
        }
    }

    protected fun getLong(key: String) = Firebase.remoteConfig[key].asLong()
    protected fun getBoolean(key: String) = Firebase.remoteConfig[key].asBoolean()
    protected fun getString(key: String) = Firebase.remoteConfig[key].asString()
    protected fun getDouble(key: String) = Firebase.remoteConfig[key].asDouble()

    companion object {
        const val DEFAULT_TIMEOUT_FETCH_SECONDS_SHORT: Long = 10
        const val DEFAULT_TIMEOUT_FETCH_SECONDS_LONG: Long = 60
    }
}
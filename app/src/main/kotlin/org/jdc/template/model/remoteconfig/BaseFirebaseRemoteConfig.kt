@file:Suppress("unused")

package org.jdc.template.model.remoteconfig

import androidx.annotation.XmlRes
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import timber.log.Timber
import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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

    fun getMinimumFetchIntervalInSeconds(): Long = TimeUnit.HOURS.toSeconds(12)

    fun getFetchTimeoutInSeconds(): Long = DEFAULT_TIMEOUT_FETCH_SECONDS_LONG

    fun fetch(now: Boolean = false): Task<Void> {
        Timber.d("RemoteConfig: fetch  now=$now")

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
        Timber.d("RemoteConfig: activate")
        return Firebase.remoteConfig.activate()
    }

    fun fetchAndActivateAsync(now: Boolean = false, onFailureBlock: () -> Unit = {}, onSuccessBlock: () -> Unit = {}) {
        Timber.d("RemoteConfig: fetchAndActivateAsync")

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
                    Timber.w("Failed to sync/fetch RemoteConfig")
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
    @Suppress("TooGenericExceptionCaught") // NEVER allow this function to crash the app
    fun fetchAndActivateNow(timeoutSeconds: Long = DEFAULT_TIMEOUT_FETCH_SECONDS_SHORT): Boolean {
        Timber.d("RemoteConfig: fetchAndActivateNow")

        // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
        val fetchTask = Firebase.remoteConfig.fetch(0L)

        // Await fetch, then activate right away if fetch was successful
        try {
            Tasks.await(fetchTask, timeoutSeconds, TimeUnit.SECONDS)
            if (fetchTask.isSuccessful) {
                Firebase.remoteConfig.activate()
                return true
            }
        } catch (e: TimeoutException) {
            Timber.w("fetchAndActivateNow timeout occurred")
        } catch (e: Exception) {
            Timber.e(e,"Failed to FetchAndActivate")
        }

        return false
    }

    fun getStatusDetails(): String {
        val info = Firebase.remoteConfig.info
        return "Last Fetch Status: [${getLastFetchStatus()}]  " +
                "Fetch: [${Instant.ofEpochMilli(info.fetchTimeMillis)}]  " +
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
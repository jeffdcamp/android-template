@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package org.jdc.template.model.config

import androidx.annotation.XmlRes
import co.touchlab.kermit.Logger
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.jdc.template.util.flow.RefreshFlow
import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

abstract class BaseFirebaseRemoteConfig {
    private val firebaseRemoteConfig: FirebaseRemoteConfig by lazy {
        val instance = Firebase.remoteConfig

        // config
        val firebaseSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(getMinimumFetchIntervalInSeconds()) // default is 12 hours... for testing/debug set to 0L
            .setFetchTimeoutInSeconds(getFetchTimeoutInSeconds())
            .build()

        instance.setConfigSettingsAsync(firebaseSettings)

        // set defaults
        // block while loading default values (prevent empty values on first launch)
        runBlocking { instance.setDefaultsAsync(getDefaults()).await() }

        return@lazy instance
    }

    private val refreshFlow = RefreshFlow()
    private val updateListeners = mutableListOf<RemoteConfigUpdateListener>()

    @XmlRes
    abstract fun getDefaults(): Int

    /**
     * Setup a listener to update the remote config when a change is fetched
     * This will automatically activate any changes that are received,
     * update any flows gotten from get*Flow(key),
     * and notify any registered RemoteConfigUpdateListeners
     */
    fun setupRealtimeUpdates(): ConfigUpdateListenerRegistration {
        return addOnConfigurationUpdateListener(object: ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                activateAsync().addOnCompleteListener {
                    refreshFlow.refresh()
                    synchronized(updateListeners) {
                        updateListeners.forEach { it.onRemoteConfigUpdated() }
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Logger.d(error) { "Failed to Update Firebase Remote Config" }
            }
        })
    }

    /**
     * Setup a listener to update the remote config when a change is fetched and activated through realtime updates
     */
    fun addRemoteConfigUpdateListener(listener: RemoteConfigUpdateListener) {
        synchronized(updateListeners) {
            updateListeners.add(listener)
        }
    }

    /**
     * Remove a listener that was added with [addRemoteConfigUpdateListener]
     */
    fun removeRemoteConfigUpdateListener(listener: RemoteConfigUpdateListener) {
        synchronized(updateListeners) {
            updateListeners.remove(listener)
        }
    }

    /**
     * Update the flows with the latest values from the remote config
     * Use this if you did not call [setupRealtimeUpdates] and need to manually update the flows
     */
    protected fun updateFlows() {
        refreshFlow.refresh()
    }

    fun getMinimumFetchIntervalInSeconds(): Long = TimeUnit.HOURS.toSeconds(12)

    fun getFetchTimeoutInSeconds(): Long = DEFAULT_TIMEOUT_FETCH_SECONDS_LONG

    /**
     * Fetches the remote config values asynchronously
     */
    @Suppress("ForbiddenVoid") // Coming from SDK
    fun fetchAsync(now: Boolean = false): Task<Void> {
        Logger.d { "RemoteConfig: fetch  now=$now" }

        return if (now) {
            // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
            // LIMIT: 5 calls per hour
            firebaseRemoteConfig.fetch(0L)
        } else {
            // Starts fetching configs, adhering to the default minimum fetch interval.
            firebaseRemoteConfig.fetch()
        }
    }

    /**
     * Fetches the remote config values and await completion
     */
    suspend fun fetch(now: Boolean = false) {
        fetchAsync(now).await()
    }

    /**
     * Activate the fetched config values asynchronously
     */
    fun activateAsync(): Task<Boolean> {
        Logger.d { "RemoteConfig: activate" }
        return firebaseRemoteConfig.activate()
    }

    /**
     * Activate the fetched config values and await completion
     */
    suspend fun activate(): Boolean {
        return activateAsync().await()
    }

    @Suppress("unused")
    fun fetchAndActivateAsync(now: Boolean = false, onFailureBlock: () -> Unit = {}, onSuccessBlock: () -> Unit = {}) {
        Logger.d { "RemoteConfig: fetchAndActivateAsync" }

        val fetchTask = if (now) {
            // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
            firebaseRemoteConfig.fetch(0L)
        } else {
            // Starts fetching configs, adhering to the default minimum fetch interval.
            firebaseRemoteConfig.fetch()
        }

        fetchTask.addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    firebaseRemoteConfig.activate().addOnCompleteListener {
                        if (it.isSuccessful) {
                            onSuccessBlock()
                        } else {
                            Logger.w { "Failed to activate after fetch RemoteConfig" }
                            onFailureBlock()
                        }
                    }
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
     * @param fetchTimeoutSeconds How long the fetch should be allowed to take before timeout will occur
     * @return true if the fetch was successful and we could apply the changes; false if there was an error fetching and activating
     */
    suspend fun fetchAndActivateNow(fetchTimeoutSeconds: Long = DEFAULT_TIMEOUT_FETCH_SECONDS_SHORT): Boolean {
        Logger.d { "RemoteConfig: fetchAndActivateNow" }

        // Starts fetching configs, adhering to the specified (0L) minimum fetch interval (fetch NOW)
        val fetchTask = firebaseRemoteConfig.fetch(0L)

        // Await fetch, then activate right away if fetch was successful
        try {
            Tasks.await(fetchTask, fetchTimeoutSeconds, TimeUnit.SECONDS)
            if (fetchTask.isSuccessful) {
                firebaseRemoteConfig.activate().await()
                return true
            }
        } catch (ignore: TimeoutException) {
            Logger.w { "fetchAndActivateNow timeout occurred" }
        } catch (expected: Exception) {
            Logger.e(expected) { "Failed to FetchAndActivate" }
        }

        return false
    }

    @Suppress("unused")
    fun getStatusDetails(): String {
        val info = firebaseRemoteConfig.info
        return "Last Fetch Status: [${getLastFetchStatus()}]  " +
                "Fetch: [${Instant.ofEpochMilli(info.fetchTimeMillis)}]  Min Fetch Interval: [${info.configSettings.minimumFetchIntervalInSeconds}s] " +
                "Fetch Timeout: [${info.configSettings.fetchTimeoutInSeconds}s]"
    }

    fun getLastFetchStatus(): String {
        return when (firebaseRemoteConfig.info.lastFetchStatus) {
            FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS -> "Success"
            FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE -> "Failure"
            FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET -> "No Fetch Yet"
            FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED -> "Throttled"
            else -> "Unknown"
        }
    }

    protected fun getLong(key: String): Long = firebaseRemoteConfig.getLong(key)
    protected fun getBoolean(key: String): Boolean = firebaseRemoteConfig.getBoolean(key)
    protected fun getString(key: String): String = firebaseRemoteConfig.getString(key)
    protected fun getDouble(key: String): Double = firebaseRemoteConfig.getDouble(key)

    /**
     * Returns a {@link Flow} of the Remote Config parameter value for the given key.
     * This will only update if [setupRealtimeUpdates] was called or [updateFlows] is called
     */
    protected fun getLongFlow(key: String): Flow<Long> = refreshFlow.mapLatest { getLong(key) }.distinctUntilChanged()
    /**
     * Returns a {@link Flow} of the Remote Config parameter value for the given key.
     * This will only update if [setupRealtimeUpdates] was called or [updateFlows] is called
     */
    protected fun getBooleanFlow(key: String): Flow<Boolean> = refreshFlow.mapLatest { getBoolean(key) }.distinctUntilChanged()
    /**
     * Returns a {@link Flow} of the Remote Config parameter value for the given key.
     * This will only update if [setupRealtimeUpdates] was called or [updateFlows] is called
     */
    protected fun getStringFlow(key: String): Flow<String> = refreshFlow.mapLatest { getString(key) }.distinctUntilChanged()
    /**
     * Returns a {@link Flow} of the Remote Config parameter value for the given key.
     * This will only update if [setupRealtimeUpdates] was called or [updateFlows] is called
     */
    protected fun getDoubleFlow(key: String): Flow<Double> = refreshFlow.mapLatest { getDouble(key) }.distinctUntilChanged()

    /**
     * Returns a {@link Set} of all Firebase Remote Config parameter keys with the given prefix.
     *
     * @param prefix The key prefix to look for. If the prefix is empty, all keys are returned.
     * @return {@link Set} of Remote Config parameter keys that start with the specified prefix.
     */
    protected fun getKeysByPrefix(prefix: String): Set<String> {
        return firebaseRemoteConfig.getKeysByPrefix(prefix)
    }

    /**
     * Starts listening for real-time config updates from the Remote Config backend and automatically
     * fetches updates from the RC backend when they are available.
     *
     * <p>If a connection to the Remote Config backend is not already open, calling this method will
     * open it. Multiple listeners can be added by calling this method again, but subsequent calls
     * re-use the same connection to the backend.
     *
     * <p>Note: Real-time Remote Config requires the Firebase Remote Config Realtime API. See the <a
     * href="https://firebase.google.com/docs/remote-config/get-started?platform=android#add-real-time-listener">Remote
     * Config Get Started</a> guide to enable the API.
     *
     * @param configUpdateListener A {@link ConfigUpdateListener} that can be used to respond to
     *     config updates when they're fetched.
     * @return A {@link ConfigUpdateListenerRegistration} that allows you to remove the added {@code
     *     configUpdateListener} and close the connection when there are no more listeners.
     */
    protected fun addOnConfigurationUpdateListener(configUpdateListener: ConfigUpdateListener): ConfigUpdateListenerRegistration {
        return firebaseRemoteConfig.addOnConfigUpdateListener(configUpdateListener)
    }

    companion object {
        const val DEFAULT_TIMEOUT_FETCH_SECONDS_SHORT: Long = 10
        const val DEFAULT_TIMEOUT_FETCH_SECONDS_LONG: Long = 60
    }
}

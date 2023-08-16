package org.jdc.template.analytics

import android.app.Activity
import android.content.Context
import android.content.Intent
import co.touchlab.kermit.Logger
import com.google.firebase.analytics.FirebaseAnalytics
import org.jdc.template.BuildConfig
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean

/**
 * DebugView for Firebase Analytics (https://firebase.google.com/docs/analytics/debugview)
 * 1. /home/<your username here>/Android/Sdk/platform-tools/adb shell setprop debug.firebase.analytics.app org.lds.ldssa.dev
 * 2. Go to Firebase Console > Analytics > DebugView
 * 3. When finished: /home/<your username here>/Android/Sdk/platform-tools/adb shell setprop debug.firebase.analytics.app .none.
 *
 */
class DefaultAnalytics(
    context: Context,
) : Analytics {
    private val dimensionsInitialized = AtomicBoolean(false)
    private var lastDimensionUpdate = LocalDateTime.MIN
    private var firebaseAnalytics: FirebaseAnalytics? = null

    init {
        // Firebase
        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        this.firebaseAnalytics = firebaseAnalytics
        updateFirebaseUserProperties()
        AppAnalytics.register(FirebaseStrategy(firebaseAnalytics))

        // Set log levels
        AppAnalytics.setLogLevel(if (BuildConfig.DEBUG) AppAnalytics.LogLevel.VERBOSE else AppAnalytics.LogLevel.UPLOAD)

        // Test Analytics in Logcat
        if (BuildConfig.DEBUG) {
            val testStrategy = TestStrategy { Logger.w { "^^^ $it" } }
            AppAnalytics.register(testStrategy)

            // must be done AFTER register() (register() calls setLogLevel())
            testStrategy.setLogLevel(AppAnalytics.LogLevel.VERBOSE)
        }
    }

    override fun upload() {
    }

    override fun setDimensions(dimensions: List<String>) {
    }

    override fun logEvent(eventId: String, attributes: Map<String, String>, scope: AppAnalytics.ScopeLevel) {
        updateDimensions()
        AppAnalytics.logEvent(eventId, attributes, scopeLevel = scope)
    }

    override fun logScreen(screen: String) {
        updateDimensions()
        AppAnalytics.logScreen(screen, scopeLevel = AppAnalytics.ScopeLevel.DEV)
    }

    override fun enableInAppNotifications(allow: Boolean) {
    }

    override fun onNewIntent(activity: Activity, intent: Intent) {
    }

    private fun updateDimensions() {
        if (dimensionsInitialized.compareAndSet(false, true) || LocalDateTime.now().isAfter(lastDimensionUpdate.plusHours(1))) {
            lastDimensionUpdate = LocalDateTime.now()

            updateFirebaseUserProperties()
        }
    }

    private fun updateFirebaseUserProperties() {
//        firebaseAnalytics?.apply {
//            val isSunday = LocalDateTime.now().dayOfWeek == DayOfWeek.SUNDAY
//            setUserProperty("xxxUserProperty", myProperty)
//        }
    }
}

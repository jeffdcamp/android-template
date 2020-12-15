package org.jdc.template.analytics

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.runBlocking
import org.jdc.template.model.repository.SettingsRepository
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean

/**
 * DebugView for Firebase Analytics (https://firebase.google.com/docs/analytics/debugview)
 * 1. /home/<your username here>/Android/Sdk/platform-tools/adb shell setprop debug.firebase.analytics.app org.lds.ldssa.dev
 * 2. Go to Firebase Console > Analytics > DebugView
 * 3. When finished: /home/<your username here>/Android/Sdk/platform-tools/adb shell setprop debug.firebase.analytics.app .none.
 *
 */
class FirebaseAnalyticsWrapper(
    context: Context,
    private val settingsRepository: SettingsRepository
) : Analytics {
    private val dimensionsInitialized = AtomicBoolean(false)
    private var lastDimensionUpdate = LocalDateTime.MIN
    private var firebaseAnalytics: FirebaseAnalytics? = null

    init {
        // Firebase
        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        this.firebaseAnalytics = firebaseAnalytics
        updateFirebaseUserProperties()
    }

    override fun logEvent(eventId: String, parameterMap: Map<String, String>?) {
        updateDimensions()

        firebaseAnalytics?.logEvent(formatValidName(eventId), createParameterMapBundle(parameterMap))
    }

    override fun logScreen(screenTitle: String, parameterMap: Map<String, String>?) {
        updateDimensions()

        val bundle = createParameterMapBundle(parameterMap)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenTitle)
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private fun createParameterMapBundle(parameterMap: Map<String, String>?): Bundle {
        val bundle = Bundle()

        parameterMap?.forEach { param ->
            bundle.putString(formatValidName(param.key), param.value)
        }

        return bundle
    }

    @SuppressLint("DefaultLocale")
    private fun formatValidName(name: String): String {
        // make sure the name is a valid event name
        // https://firebase.google.com/docs/reference/cpp/group/event-names
        return name.trim().toLowerCase().replace(spaceRegex, "_").take(MAX_EVENT_NAME_LENGTH)
    }

    private fun updateDimensions() {
        if (dimensionsInitialized.compareAndSet(false, true) || LocalDateTime.now().isAfter(lastDimensionUpdate.plusHours(1))) {
            updateFirebaseUserProperties()
        }
    }

    private fun updateFirebaseUserProperties() {
        firebaseAnalytics?.apply {
            setUserProperty("app_instance_id", runBlocking { settingsRepository.getAppInstanceId() })
        }
    }

    companion object {
        private const val MAX_EVENT_NAME_LENGTH = 40
        private val spaceRegex = """\s+""".toRegex()
    }
}

@file:Suppress("unused")

package org.jdc.template.analytics

import android.annotation.SuppressLint
import android.os.Bundle
import co.touchlab.kermit.Logger
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.Locale

class FirebaseStrategy(
    private val firebaseAnalytics: FirebaseAnalytics
) : AppAnalytics.Strategy {

    private var logLevel = AppAnalytics.LogLevel.NONE

    var eventScopeLevel = AppAnalytics.DEFAULT_EVENT_SCOPE_LEVEL
    var screenScopeLevel = AppAnalytics.DEFAULT_SCREEN_SCOPE_LEVEL

    override fun logEvent(event: AnalyticEvent) {
        if (event.scopeLevel.ordinal > eventScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logEvent(${event.id})")
        consoleLogParameterMap(event.params)

        firebaseAnalytics.logEvent(formatValidName(event.id), createParameterMapBundle(event.params))
    }

    override fun logScreen(screen: AnalyticScreen) {
        if (screen.scopeLevel.ordinal > screenScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logScreen(${screen.screenTitle})")
        consoleLogParameterMap(screen.params)

        val bundle = createParameterMapBundle(screen.params)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen.screenTitle)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private fun createParameterMapBundle(parameterMap: Map<String, String>?): Bundle {
        val bundle = Bundle()

        parameterMap?.forEach { param ->
            bundle.putString(formatValidName(param.key), param.value)
        }

        return bundle
    }

    @SuppressLint("DefaultLocale")
    fun formatValidName(name: String): String {
        return name.trim().lowercase(Locale.getDefault()).replace(invalidCharactersRegex, "_").take(MAX_EVENT_NAME_LENGTH)
    }

    override fun logError(error: AnalyticError) {
        // Not logging errors to Firebase
    }

    override fun setLogLevel(logLevel: AppAnalytics.LogLevel, enableProviderLogging: Boolean) {
        this.logLevel = logLevel
    }

    private fun consoleLogMessage(level: AppAnalytics.LogLevel, message: String) {
        if (level.ordinal <= logLevel.ordinal) {
            Logger.d { message }
        }
    }

    private fun consoleLogParameterMap(parameterMap: Map<String, String>?) {
        if (logLevel.ordinal >= AppAnalytics.LogLevel.VERBOSE.ordinal) {
            parameterMap?.keys?.forEach {
                consoleLogMessage(AppAnalytics.LogLevel.VERBOSE, "  $it:${parameterMap[it]}")
            }
        }
    }

    companion object {
        private const val MAX_EVENT_NAME_LENGTH = 40
        private val invalidCharactersRegex = """(\s+|[^a-z0-9_])""".toRegex()
    }
}

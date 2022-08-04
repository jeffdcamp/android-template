package org.jdc.template.analytics

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.File

class TestStrategy(
    private val tag: String = "Test",
    private val logPriority: Int = Log.DEBUG
) : AppAnalytics.Strategy {


    private var logLevel = AppAnalytics.LogLevel.NONE
    private var providerLoggingEnabled = false

    var eventScopeLevel = AppAnalytics.DEFAULT_EVENT_SCOPE_LEVEL
    var screenScopeLevel = AppAnalytics.DEFAULT_SCREEN_SCOPE_LEVEL
    var errorScopeLevel = AppAnalytics.DEFAULT_ERROR_SCOPE_LEVEL

    // NOTE:  Setting this value will cause a write to json file on EVERY event (only use for debugging/QA)
    var logToJsonFile: File? = null

    private val analyticsList = mutableListOf<Analytic>()
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = true
        prettyPrint = true
    }

    override fun setLogLevel(logLevel: AppAnalytics.LogLevel, enableProviderLogging: Boolean) {
        this.logLevel = logLevel
        this.providerLoggingEnabled = enableProviderLogging
    }

    override fun logError(errorMessage: String, errorClass: String, scopeLevel: AppAnalytics.ScopeLevel) {
        if (scopeLevel.ordinal > errorScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logError($errorMessage)")
        if (logToJsonFile != null) {
            analyticsList.add(Analytic(AnalyticType.ERROR, "errorMessage"))
            writeToJsonFile()
        }
    }

    override fun logEvent(eventId: String, parameterMap: Map<String, String>, scopeLevel: AppAnalytics.ScopeLevel) {
        if (scopeLevel.ordinal > eventScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logEvent($eventId)")
        consoleLogParameterMap(parameterMap)

        if (logToJsonFile != null) {
            analyticsList.add(Analytic(AnalyticType.EVENT, eventId, parameterMap))
            writeToJsonFile()
        }
    }

    override fun logScreen(screenTitle: String, parameterMap: Map<String, String>, scopeLevel: AppAnalytics.ScopeLevel) {
        if (scopeLevel.ordinal > screenScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logScreen($screenTitle)")
        consoleLogParameterMap(parameterMap)

        if (logToJsonFile != null) {
            analyticsList.add(Analytic(AnalyticType.SCREEN, screenTitle, parameterMap))
            writeToJsonFile()
        }
    }

    private fun consoleLogMessage(level: AppAnalytics.LogLevel, message: String) {
        if (level.ordinal <= logLevel.ordinal) {
            Timber.log(logPriority, "$tag: DEBUG: $message")
        }
    }

    private fun consoleLogParameterMap(parameterMap: Map<String, String>) {
        if (logLevel.ordinal >= AppAnalytics.LogLevel.VERBOSE.ordinal) {
            parameterMap.keys.forEach {
                consoleLogMessage(AppAnalytics.LogLevel.VERBOSE, "  $it:${parameterMap[it]}")
            }
        }
    }

    private fun writeToJsonFile() {
        val logFile = logToJsonFile ?: return

        // Timber.e("Writing to [${logFile.absolutePath}]")
        logFile.writeText(json.encodeToString(ListSerializer(Analytic.serializer()), analyticsList))
    }

    @Serializable
    private data class Analytic(val type: AnalyticType, val name: String, val properties: Map<String, String> = emptyMap())
    private enum class AnalyticType {
        EVENT, SCREEN, ERROR
    }
}
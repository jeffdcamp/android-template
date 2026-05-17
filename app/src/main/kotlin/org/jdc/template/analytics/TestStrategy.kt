package org.jdc.template.analytics

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path

class TestStrategy(
    private val logBlock: (message: String) -> Unit
) : AppAnalytics.Strategy {


    private var logLevel = AppAnalytics.LogLevel.NONE
    private var providerLoggingEnabled = false

    var eventScopeLevel = AppAnalytics.DEFAULT_EVENT_SCOPE_LEVEL
    var screenScopeLevel = AppAnalytics.DEFAULT_SCREEN_SCOPE_LEVEL
    var errorScopeLevel = AppAnalytics.DEFAULT_ERROR_SCOPE_LEVEL

    // NOTE:  Setting this value will cause a write to json file on EVERY event (only use for debugging/QA)
    val filesystem = FileSystem.SYSTEM
    var logToJsonFile: Path? = null

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

    override fun logError(error: AnalyticError) {
        if (error.scopeLevel.ordinal > errorScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logError(${error.message})")
        if (logToJsonFile != null) {
            analyticsList.add(Analytic(AnalyticType.ERROR, error.message))
            writeToJsonFile()
        }
    }

    override fun logEvent(event: AnalyticEvent) {
        if (event.scopeLevel.ordinal > eventScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logEvent(${event.id})")
        consoleLogParameterMap(event.params)

        if (logToJsonFile != null) {
            analyticsList.add(Analytic(AnalyticType.EVENT, event.id, event.params.orEmpty()))
            writeToJsonFile()
        }
    }

    override fun logScreen(screen: AnalyticScreen) {
        if (screen.scopeLevel.ordinal > screenScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logScreen(${screen.screenTitle})")
        consoleLogParameterMap(screen.params)

        if (logToJsonFile != null) {
            analyticsList.add(Analytic(AnalyticType.SCREEN, screen.screenTitle, screen.params.orEmpty()))
            writeToJsonFile()
        }
    }

    private fun consoleLogMessage(level: AppAnalytics.LogLevel, message: String) {
        if (level.ordinal <= logLevel.ordinal) {
            logBlock(message)
        }
    }

    private fun consoleLogParameterMap(parameterMap: Map<String, String>?) {
        if (logLevel.ordinal >= AppAnalytics.LogLevel.VERBOSE.ordinal) {
            parameterMap?.keys?.forEach {
                consoleLogMessage(AppAnalytics.LogLevel.VERBOSE, "  $it:${parameterMap[it]}")
            }
        }
    }

    private fun writeToJsonFile() {
        val logFile = logToJsonFile ?: return
        filesystem.write(logFile) { writeUtf8(json.encodeToString(ListSerializer(Analytic.serializer()), analyticsList)) }
    }

    @Serializable
    private data class Analytic(val type: AnalyticType, val name: String, val properties: Map<String, String> = emptyMap())
    private enum class AnalyticType {
        EVENT, SCREEN, ERROR
    }
}

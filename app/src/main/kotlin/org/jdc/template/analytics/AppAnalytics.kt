package org.jdc.template.analytics

object AppAnalytics {

    private val strategies: MutableList<Strategy> = ArrayList()
    private var logLevel = LogLevel.NONE
    val DEFAULT_EVENT_SCOPE_LEVEL = ScopeLevel.DEV
    val DEFAULT_SCREEN_SCOPE_LEVEL = ScopeLevel.DEV
    val DEFAULT_ERROR_SCOPE_LEVEL = ScopeLevel.DEV
    private var enableProviderLogging = false

    fun register(strategy: Strategy) {
        strategy.setLogLevel(logLevel, enableProviderLogging)
        strategies.add(strategy)
    }

    fun getRegistered(): List<Strategy> = strategies

    inline fun <reified T: Strategy> findRegistered(): List<T> {
        val results = ArrayList<T>()

        getRegistered().forEach {
            if (it is T) {
                results.add(it)
            }
        }

        return results
    }

    fun logEvent(eventId: String, parameterMap: Map<String, String> = HashMap(), scopeLevel: ScopeLevel = DEFAULT_EVENT_SCOPE_LEVEL) {
        strategies.forEach {
            it.logEvent(eventId, parameterMap, scopeLevel)
        }
    }

    fun logScreen(screenTitle: String, parameterMap: Map<String, String> = HashMap(), scopeLevel: ScopeLevel = DEFAULT_SCREEN_SCOPE_LEVEL) {
        strategies.forEach {
            it.logScreen(screenTitle, parameterMap, scopeLevel)
        }
    }

    fun logError(errorMessage: String, errorClass: String, scopeLevel: ScopeLevel = DEFAULT_ERROR_SCOPE_LEVEL) {
        strategies.forEach {
            it.logError(errorMessage, errorClass, scopeLevel)
        }
    }

    fun setLogLevel(logLevel: LogLevel, enableProviderLogging: Boolean = false) {
        this.logLevel = logLevel
        this.enableProviderLogging = enableProviderLogging

        strategies.forEach {
            it.setLogLevel(logLevel, enableProviderLogging)
        }
    }

    enum class LogLevel {
        NONE,
        UPLOAD,
        EVENT,
        SESSION,
        VERBOSE
    }

    enum class ScopeLevel {
        BUSINESS,
        DEV
    }

    interface Strategy {
        fun logEvent(eventId: String, parameterMap: Map<String, String> = HashMap(), scopeLevel: ScopeLevel = DEFAULT_EVENT_SCOPE_LEVEL)

        fun logScreen(screenTitle: String, parameterMap: Map<String, String> = HashMap(), scopeLevel: ScopeLevel = DEFAULT_SCREEN_SCOPE_LEVEL)

        fun logError(errorMessage: String, errorClass: String, scopeLevel: ScopeLevel = DEFAULT_ERROR_SCOPE_LEVEL)

        fun setLogLevel(logLevel: LogLevel, enableProviderLogging: Boolean = false)
    }
}
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

    fun logEvent(event: AnalyticEvent) {
        strategies.forEach {
            it.logEvent(event)
        }
    }

    fun logScreen(screen: AnalyticScreen) {
        strategies.forEach {
            it.logScreen(screen)
        }
    }

    fun logError(error: AnalyticError) {
        strategies.forEach {
            it.logError(error)
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
        fun logEvent(event: AnalyticEvent)
        fun logScreen(screen: AnalyticScreen)
        fun logError(error: AnalyticError)
        fun setLogLevel(logLevel: LogLevel, enableProviderLogging: Boolean = false)
    }
}
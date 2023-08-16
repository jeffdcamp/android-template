package org.jdc.template.util.log

import co.touchlab.crashkios.crashlytics.CrashlyticsCalls
import co.touchlab.crashkios.crashlytics.CrashlyticsCallsActual
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag

/**
 * Copy of Kermit CrashlyticsLogWriter with following changes:
 * - removed requirement for an exception for non-fatal logging (added LoggerNonFatalCrashLogException())
 */
class SimpleCrashlyticsLogWriter(
    private val minSeverity: Severity = Severity.Info,
    private val minCrashSeverity: Severity? = Severity.Warn,
    private val messageStringFormatter: MessageStringFormatter = DefaultFormatter
) : LogWriter() {

    private val crashlyticsCalls: CrashlyticsCalls = CrashlyticsCallsActual()

    init {
        require (minCrashSeverity != null && minSeverity < minCrashSeverity) {
            "minSeverity ($minSeverity) cannot be greater than minCrashSeverity ($minCrashSeverity)"
        }

        enableCrashlytics()
    }

    override fun isLoggable(tag: String, severity: Severity): Boolean = severity >= minSeverity

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        crashlyticsCalls.logMessage(
            messageStringFormatter.formatMessage(severity, Tag(tag), Message(message))
        )

        if (minCrashSeverity != null && severity >= minCrashSeverity) {
            if (throwable != null) {
                crashlyticsCalls.sendHandledException(throwable)
            } else {
                crashlyticsCalls.sendHandledException(LogToCrashlyticsException(message))
            }
        }
    }
}
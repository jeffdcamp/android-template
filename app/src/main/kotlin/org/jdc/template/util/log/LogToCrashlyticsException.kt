package org.jdc.template.util.log

/**
 * Non-fatal exception for use with Logger and Crashlytics
 */
class LogToCrashlyticsException(message: String) : RuntimeException(message)
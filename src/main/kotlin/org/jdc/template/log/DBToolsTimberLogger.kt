package org.jdc.template.log

import org.dbtools.android.domain.log.DBToolsLogger

import timber.log.Timber

class DBToolsTimberLogger : DBToolsLogger {
    override fun v(tag: String, message: String) {
        Timber.v(formatMessage(tag, message))
    }

    override fun v(tag: String, message: String, t: Throwable) {
        Timber.v(t, formatMessage(tag, message))
    }

    override fun d(tag: String, message: String) {
        Timber.d(formatMessage(tag, message))
    }

    override fun d(tag: String, message: String, t: Throwable) {
        Timber.d(t, formatMessage(tag, message))
    }

    override fun i(tag: String, message: String) {
        Timber.i(formatMessage(tag, message))
    }

    override fun i(tag: String, message: String, t: Throwable) {
        Timber.i(t, formatMessage(tag, message))
    }

    override fun w(tag: String, message: String) {
        Timber.w(formatMessage(tag, message))
    }

    override fun w(tag: String, message: String, t: Throwable) {
        Timber.w(t, formatMessage(tag, message))
    }

    override fun e(tag: String, message: String) {
        Timber.e(formatMessage(tag, message))
    }

    override fun e(tag: String, message: String, t: Throwable) {
        Timber.e(t, formatMessage(tag, message))
    }

    override fun wtf(tag: String, message: String) {
        Timber.wtf(formatMessage(tag, message))
    }

    override fun wtf(tag: String, message: String, t: Throwable) {
        Timber.wtf(t, formatMessage(tag, message))
    }

    private fun formatMessage(tag: String?, message: String): String {
        if (tag != null && !tag.isEmpty()) {
            return "[$tag] $message"
        } else {
            return message
        }
    }
}

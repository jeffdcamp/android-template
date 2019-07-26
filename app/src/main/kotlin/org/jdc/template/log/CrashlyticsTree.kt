package org.jdc.template.log

import android.util.Log
import com.crashlytics.android.Crashlytics

import timber.log.Timber
import java.util.*

class CrashlyticsTree : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.ERROR
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            if (t != null) {
                Crashlytics.log(1, "message", message)
                Crashlytics.logException(t)
            } else {
                Crashlytics.logException(TimberNonFatalCrashLogException(message))
            }
        }
    }
}

/**
 * Non-fatal exception for use with Timber and Crashlytics
 */
class TimberNonFatalCrashLogException(message: String) : RuntimeException(message) {

    override fun fillInStackTrace(): Throwable {
        super.fillInStackTrace()
        val original = stackTrace
        val iterator = listOf(*original).iterator()
        val filtered = ArrayList<StackTraceElement>()

        // heading to top of Timber stack trace
        while (iterator.hasNext()) {
            val stackTraceElement = iterator.next()
            if (isTimber(stackTraceElement)) {
                break
            }
        }

        // copy all
        var isReachedApp = false
        while (iterator.hasNext()) {
            val stackTraceElement = iterator.next()
            // skip Timber
            if (!isReachedApp && isTimber(stackTraceElement)) {
                continue
            }
            isReachedApp = true
            filtered.add(stackTraceElement)
        }

        stackTrace = filtered.toTypedArray()
        return this
    }

    private fun isTimber(stackTraceElement: StackTraceElement): Boolean {
        return stackTraceElement.className == Timber::class.java.name
    }
}

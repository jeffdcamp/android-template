package org.jdc.template.log

import android.util.Log

import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.ERROR
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            if (t != null) {
//                Crashlytics.log(1, "message", message)
//                Crashlytics.logException(t)
            } else {
//                Crashlytics.logException(new NonFatalCrashLogException(message))
            }
        }
    }

    inner class NonFatalCrashLogException(message: String) : RuntimeException(message)
}

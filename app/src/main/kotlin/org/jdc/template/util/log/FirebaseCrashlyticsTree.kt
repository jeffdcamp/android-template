package org.jdc.template.util.log

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class FirebaseCrashlyticsTree : Timber.Tree() {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.ERROR
    }

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            if (t != null) {
                crashlytics.log(message)
                crashlytics.recordException(t)
            } else {
                crashlytics.recordException(TimberNonFatalCrashLogException(message))
            }
        }
    }
} 
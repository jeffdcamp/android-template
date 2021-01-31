package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import org.jdc.template.BuildConfig
import org.jdc.template.util.log.DebugTree
import org.jdc.template.util.log.FirebaseCrashlyticsTree
import org.jdc.template.util.log.ReleaseTree
import timber.log.Timber

class LoggingInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        @Suppress("ConstantConditionIf") // set in build.gradle file
        if (BuildConfig.BUILD_TYPE != "debug") {
            // Plant Crashlytics
            // Timber.e(...) will log a non-fatal crash in Crashlytics
            Timber.plant(FirebaseCrashlyticsTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
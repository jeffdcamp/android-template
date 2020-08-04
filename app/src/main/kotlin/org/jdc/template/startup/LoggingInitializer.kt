package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.jdc.template.BuildConfig
import org.jdc.template.log.DebugTree
import org.jdc.template.log.FirebaseCrashlyticsTree
import org.jdc.template.log.ReleaseTree
import org.jdc.template.model.prefs.Prefs
import timber.log.Timber

class LoggingInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val applicationContext = checkNotNull(context.applicationContext) { "Missing Application Context" }
        val injector = EntryPoints.get(applicationContext, LoggingInitializerInjector::class.java)

        FirebaseCrashlytics.getInstance().setUserId(injector.getPrefs().getAppInstanceId())

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
        return listOf(PrefsInitializer::class.java)
    }

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface LoggingInitializerInjector {
        fun getPrefs(): Prefs
    }
}
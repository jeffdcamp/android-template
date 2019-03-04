package org.jdc.template

import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jdc.template.inject.Injector
import org.jdc.template.log.DebugTree
import org.jdc.template.log.ReleaseTree
import org.jdc.template.prefs.PrefsManager
import org.jdc.template.ui.notifications.NotificationChannels
import timber.log.Timber

class App : MultiDexApplication() {

    init {
        Injector.init(this)
        PrefsManager.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        // Initialize dependency injection
        Injector.get().inject(this)

        setupLogging()

        // register notification channels
        NotificationChannels.registerAllChannels(this)
    }

    private fun setupLogging() {
        // Always register Crashltyics (even if CrashlyticsTree is not planted)
        //        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        @Suppress("ConstantConditionIf") // set in build.gradle file
        if (BuildConfig.BUILD_TYPE != "debug") {
            // Plant Crashlytics
            // Log.e(...) will log a non-fatal crash in Crashlytics
            // Timber.plant(new CrashlyticsTree());
        }
    }
}

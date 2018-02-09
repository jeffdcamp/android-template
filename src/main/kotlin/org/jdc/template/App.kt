package org.jdc.template

import android.support.multidex.MultiDexApplication
import com.evernote.android.job.JobManager
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jdc.template.inject.Injector
import org.jdc.template.job.AppJobCreator
import org.jdc.template.log.DebugTree
import org.jdc.template.log.ReleaseTree
import org.jdc.template.prefs.base.PrefsManager
import org.jdc.template.ui.notifications.NotificationChannels
import timber.log.Timber
import javax.inject.Inject

class App : MultiDexApplication() {

    @Inject
    lateinit var appJobCreator: AppJobCreator

    init {
        Injector.init(this)
        PrefsManager.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        // Initialize dependency injection
        Injector.get().inject(this)

        JobManager.create(this).addJobCreator(appJobCreator)

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

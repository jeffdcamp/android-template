package org.jdc.template

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.evernote.android.job.JobManager
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jdc.template.inject.Injector
import org.jdc.template.job.AppJobCreator
import org.jdc.template.log.DebugTree
import org.jdc.template.log.ReleaseTree
import org.jdc.template.prefs.base.PrefsManager
import org.jdc.template.ui.notifications.NotificationChannels
import timber.log.Timber

class App : Application() {

    init {
        PrefsManager.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Injector.init(this)
        JobManager.create(this).addJobCreator(AppJobCreator())

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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (filesDir != null) {
            MultiDex.install(this)
        } else {
            // During app install it might have experienced "INSTALL_FAILED_DEXOPT" (reinstall is the only known work-around)
            // https://code.google.com/p/android/issues/detail?id=8886
            val message = getString(R.string.app_name) + " is in a bad state, please uninstall/reinstall"
            Timber.e(message)
        }
    }
}

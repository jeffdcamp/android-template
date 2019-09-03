package org.jdc.template

import android.app.Application
import androidx.work.Configuration
import com.crashlytics.android.Crashlytics
import com.jakewharton.threetenabp.AndroidThreeTen
import com.vikingsen.inject.worker.WorkerFactory
import io.fabric.sdk.android.Fabric
import org.jdc.template.inject.Injector
import org.jdc.template.log.CrashlyticsTree
import org.jdc.template.log.DebugTree
import org.jdc.template.log.ReleaseTree
import org.jdc.template.prefs.Prefs
import org.jdc.template.prefs.PrefsManager
import org.jdc.template.ui.notifications.NotificationChannels
import timber.log.Timber
import javax.inject.Inject

class App : Application(), Configuration.Provider {

    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var workerFactory: WorkerFactory

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
        Fabric.with(this, Crashlytics())
        Crashlytics.setUserIdentifier(prefs.getAppInstanceId())

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        @Suppress("ConstantConditionIf") // set in build.gradle file
        if (BuildConfig.BUILD_TYPE != "debug") {
            // Plant Crashlytics
            // Log.e(...) will log a non-fatal crash in Crashlytics
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
    }
}

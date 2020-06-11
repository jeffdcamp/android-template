package org.jdc.template

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import org.jdc.template.log.DebugTree
import org.jdc.template.log.FirebaseCrashlyticsTree
import org.jdc.template.log.ReleaseTree
import org.jdc.template.prefs.Prefs
import org.jdc.template.prefs.PrefsManager
import org.jdc.template.ui.ThemeManager
import org.jdc.template.ui.notifications.NotificationChannels
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var prefs: Prefs

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var themeManager: ThemeManager

    init {
        PrefsManager.init(this)
    }

    override fun onCreate() {
        super.onCreate()

        setupLogging()

        // register notification channels
        NotificationChannels.registerAllChannels(this)

        // Apply theme
        themeManager.applyTheme()
    }

    private fun setupLogging() {
        FirebaseCrashlytics.getInstance().setUserId(prefs.getAppInstanceId())

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

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}

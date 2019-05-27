package org.jdc.template.inject

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.work.WorkManager
import com.google.android.gms.analytics.GoogleAnalytics
import dagger.Module
import dagger.Provides
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.model.webservice.ServiceModule
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = [ServiceModule::class, AssistedModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideAnalytics(): Analytics {
        // Only send analytics to Google Analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return object : Analytics {
                override fun send(params: Map<String, String>) {
                    Timber.d("Analytics Params [$params]")
                }
            }
        }

        val googleAnalytics = GoogleAnalytics.getInstance(application)
        val tracker = googleAnalytics.newTracker(BuildConfig.ANALYTICS_KEY)
        // tracker.setSessionTimeout(300); // default is 30 seconds
        return Analytics.GoogleAnalytics(tracker)
    }

    @Provides
    @Singleton
    fun provideWorkManager(): WorkManager {
        return WorkManager.getInstance()
    }
}

package org.jdc.template.dagger

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.webservice.ServiceModule
import org.jdc.template.webservice.converter.DateTimeTypeConverter
import org.threeten.bp.LocalDateTime
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(ServiceModule::class))
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
    fun provideNotificationManager(application: Application): NotificationManager {
        return application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideAnalytics(): Analytics {
        // Only send analytics to Google Analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return object : Analytics {
                override fun send(params: Map<String, String>) {
                    Log.d("Analytics", params.toString())
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
    internal fun provideGson(): Gson {
        val builder = GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, DateTimeTypeConverter())
//                .setPrettyPrinting() // NOSONAR - DEBUG

        return builder.create()
    }

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}

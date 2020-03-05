package org.jdc.template.inject

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.model.webservice.ServiceModule
import org.jdc.template.prefs.Prefs
import javax.inject.Singleton

@Module(includes = [ServiceModule::class, AssistModule::class])
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
    fun provideJson(): Json {
        return Json(JsonConfiguration.Stable.copy(
                ignoreUnknownKeys = true,
                serializeSpecialFloatingPointValues = true,
                useArrayPolymorphism = true
        ))
    }

    @Provides
    @Singleton
    fun provideAnalytics(prefs: Prefs): Analytics {
        // Only send analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return Analytics.DebugAnalytics()
        }

        val firebaseAnalytics = FirebaseAnalytics.getInstance(application).apply {
            setUserId(prefs.getAppInstanceId())
        }
        return Analytics.FirebaseAnalyticsWrapper(firebaseAnalytics)
    }
}

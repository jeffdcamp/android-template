package org.jdc.template.inject

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import org.jdc.template.BuildConfig
import org.jdc.template.analytics.Analytics
import org.jdc.template.analytics.DebugAnalytics
import org.jdc.template.analytics.FirebaseAnalyticsWrapper
import org.jdc.template.model.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
        }
    }

    @Provides
    @Singleton
    fun provideAnalytics(@ApplicationContext context: Context, settingsRepository: SettingsRepository): Analytics {
        // Only send analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return DebugAnalytics()
        }

        return FirebaseAnalyticsWrapper(context, settingsRepository)
    }
}

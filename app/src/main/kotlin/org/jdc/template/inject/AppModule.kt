package org.jdc.template.inject

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okio.FileSystem
import org.jdc.template.analytics.Analytics
import org.jdc.template.analytics.DefaultAnalytics
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
            explicitNulls = false
        }
    }

    @Provides
    @Singleton
    fun provideAnalytics(@ApplicationContext context: Context): Analytics {
        return DefaultAnalytics(context)
    }

    @Provides
    @Singleton
    fun provideFilesystem(): FileSystem {
        return FileSystem.SYSTEM
    }
}

package org.jdc.template.inject

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okio.FileSystem
import org.jdc.template.analytics.Analytics
import org.jdc.template.analytics.DefaultAnalytics
import org.jdc.template.shared.model.db.main.MainDatabase
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

    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        val dbFile = application.getDatabasePath(MainDatabase.DATABASE_NAME)
        return Room.databaseBuilder<MainDatabase>(
            context = application,
            name = dbFile.absolutePath,
        ).build()
    }
}

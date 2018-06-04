package org.jdc.template.inject

import android.app.Application
import android.app.NotificationManager
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomMasterTable.TABLE_NAME
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.datasource.database.main.MainDatabase
import org.jdc.template.datasource.webservice.ServiceModule
import org.jdc.template.json.LocalDateTimeTypeConverter
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDateTime
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
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
                    Timber.d("Analytics Params [${params.toString()}]")
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
    fun provideGson(): Gson {
        val builder = GsonBuilder()
//                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeConverter())
        return builder.create()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProvider.MainCoroutineContextProvider
    }

    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(application, MainDatabase::class.java, MainDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
//                .addMigrations(object: Migration(1, 2) {
//                    override fun migrate(p0: SupportSQLiteDatabase?) {
//                    }
//                })
//                .openHelperFactory(SqliteOrgSQLiteOpenHelperFactory())
                .build()
    }

    // The docs say: Caution: To keep your migration logic functioning as expected, use full queries
    // instead of referencing constants that represent the queries.
    // It seems we have chosen to use the constants.  At least that is what is in the json and the
    // commented code above.

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            try {
                database.execSQL("ALTER TABLE ${TABLE_NAME} ADD COLUMN profileUrl TEXT NOT NULL DEFAULT ''")
            } catch (ex : Exception) {
                Timber.e(ex.message)
            }
        }
    }
}


package org.jdc.template.inject

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okio.FileSystem
import org.jdc.template.analytics.Analytics
import org.jdc.template.analytics.DefaultAnalytics
import org.jdc.template.shared.domain.usecase.CreateIndividualLargeTestDataUseCase
import org.jdc.template.shared.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.template.shared.model.datastore.DatastoreUtil
import org.jdc.template.shared.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.template.shared.model.datastore.DeviceDataStore
import org.jdc.template.shared.model.datastore.DevicePreferenceDataSource
import org.jdc.template.shared.model.datastore.UserDataStore
import org.jdc.template.shared.model.datastore.UserPreferenceDataSource
import org.jdc.template.shared.model.db.main.MainDatabase
import org.jdc.template.shared.model.repository.ChatRepository
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.shared.model.repository.SettingsRepository
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
        val builder = Room.databaseBuilder<MainDatabase>(
            context = application,
            name = dbFile.absolutePath,
        )

        return MainDatabase.getDatabase(builder)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceDataSource(application: Application): UserPreferenceDataSource {
        val dataStore = UserPreferenceDataSource.createDataStore {
            application.filesDir.resolve(createDataStoreFilename(DatastoreUtil.USER)).absolutePath
        }

        return UserPreferenceDataSource(UserDataStore(dataStore))
    }

    @Provides
    @Singleton
    fun provideDevicePreferenceDataSource(application: Application): DevicePreferenceDataSource {
        val dataStore = DevicePreferenceDataSource.createDataStore {
            application.filesDir.resolve(createDataStoreFilename(DatastoreUtil.DEVICE)).absolutePath
        }

        return DevicePreferenceDataSource(DeviceDataStore(dataStore))
    }

    @Provides
    @Singleton
    fun provideIndividualRepository(
        mainDatabase: MainDatabase,
        settingsRepository: SettingsRepository,
    ): IndividualRepository {
        return IndividualRepository(mainDatabase, settingsRepository)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        mainDatabase: MainDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        @ApplicationScope appScope: CoroutineScope
    ): ChatRepository {
        return ChatRepository(mainDatabase, ioDispatcher, appScope)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        userPreferenceDataSource: UserPreferenceDataSource,
        devicePreferenceDataSource: DevicePreferenceDataSource,
        @ApplicationScope appScope: CoroutineScope
    ): SettingsRepository {
        return SettingsRepository(userPreferenceDataSource, devicePreferenceDataSource, appScope)
    }

    @Provides
    fun provideCreateIndividualTestDataUseCase(
        individualRepository: IndividualRepository,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): CreateIndividualTestDataUseCase {
        return CreateIndividualTestDataUseCase(individualRepository, defaultDispatcher)
    }

    @Provides
    fun provideCreateIndividualLargeTestDataUseCase(
        individualRepository: IndividualRepository,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): CreateIndividualLargeTestDataUseCase {
        return CreateIndividualLargeTestDataUseCase(individualRepository, defaultDispatcher)
    }
}

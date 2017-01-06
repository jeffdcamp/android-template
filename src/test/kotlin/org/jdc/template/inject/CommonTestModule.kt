package org.jdc.template.inject

import android.app.Application
import dagger.Module
import dagger.Provides
import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.DatabaseConfig
import org.dbtools.android.domain.database.JdbcSqliteDatabaseWrapper
import org.jdc.template.TestFilesystem
import org.jdc.template.model.database.DatabaseManager
import org.mockito.AdditionalMatchers.or
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.isNull
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import java.io.File
import javax.inject.Singleton

@Module
class CommonTestModule {
    // ========== ANDROID ==========
    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        val application = mock(Application::class.java)

        `when`(application.filesDir).thenReturn(TestFilesystem.INTERNAL_FILES_DIR)

        doAnswer { invocation ->
            val type = invocation.getArgument<String>(0)
            if (type != null) {
                return@doAnswer File(TestFilesystem.EXTERNAL_FILES_DIR, type)
            } else {
                return@doAnswer TestFilesystem.EXTERNAL_FILES_DIR
            }
        }.`when`(application).getExternalFilesDir(or(isNull(String::class.java), anyString()))

        return application
    }

    @Provides
    @Singleton
    internal fun provideDatabaseManager(databaseConfig: DatabaseConfig): DatabaseManager {
        val databaseManager = spy(DatabaseManager(databaseConfig))

        // don't allow the database to be upgraded
        doNothing().`when`(databaseManager).onUpgrade(any(AndroidDatabase::class.java), anyInt(), anyInt())

        JdbcSqliteDatabaseWrapper.setEnableLogging(true)

        return databaseManager
    }
}

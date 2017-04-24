package org.jdc.template.ux.individual

import dagger.Module
import dagger.Provides
import org.dbtools.android.domain.config.DatabaseConfig
import org.jdc.template.model.database.TestMainDatabaseConfig
import javax.inject.Singleton

// Test Module
@Module
class IndividualPresenterTestModule {
    @Provides
    @Singleton
    fun provideDatabaseConfig(): DatabaseConfig {
        return TestMainDatabaseConfig.instance
    }
}
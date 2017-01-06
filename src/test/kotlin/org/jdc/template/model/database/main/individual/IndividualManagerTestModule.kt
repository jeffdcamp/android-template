package org.jdc.template.model.database.main.individual

import dagger.Module
import dagger.Provides
import org.dbtools.android.domain.config.DatabaseConfig
import org.jdc.template.model.database.TestMainDatabaseConfig
import javax.inject.Singleton

@Module
class IndividualManagerTestModule {
    @Provides
    @Singleton
    internal fun provideDatabaseConfig(): DatabaseConfig {
        return TestMainDatabaseConfig.instance
    }
}

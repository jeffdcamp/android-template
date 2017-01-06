package org.jdc.template.inject

import dagger.Module
import dagger.Provides
import org.dbtools.android.domain.config.DatabaseConfig
import org.jdc.template.model.database.TestMainDatabaseConfig
import javax.inject.Singleton

@Module
class DbBlankTestModule {
    @Provides
    @Singleton
    internal fun provideDatabaseConfig(): DatabaseConfig {
        return TestMainDatabaseConfig.instance
    }
}

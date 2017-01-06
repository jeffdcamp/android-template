package org.jdc.template.inject;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.jdc.template.model.database.TestMainDatabaseConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbBlankTestModule {
    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig() {
        return TestMainDatabaseConfig.getInstance();
    }
}

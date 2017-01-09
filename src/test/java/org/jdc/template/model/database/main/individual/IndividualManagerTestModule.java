package org.jdc.template.model.database.main.individual;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.jdc.template.model.database.TestMainDatabaseConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IndividualManagerTestModule {
    public IndividualManagerTestModule() {
    }

    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig() {
        return TestMainDatabaseConfig.getInstance();
    }
}

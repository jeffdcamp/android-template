package org.jdc.template.model.database.main.individual;

import org.dbtools.android.domain.config.BuildEnv;
import org.dbtools.android.domain.config.TestDatabaseConfig;
import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.DatabaseManagerConst;

import javax.annotation.Nonnull;

public class TestMainDatabaseConfig extends TestDatabaseConfig {
    public TestMainDatabaseConfig(@Nonnull String databaseFilename) {
        super(DatabaseManagerConst.MAIN_DATABASE_NAME, BuildEnv.GRADLE, databaseFilename, DatabaseManager.MAIN_VERSION, DatabaseManager.MAIN_VIEWS_VERSION);
    }
}

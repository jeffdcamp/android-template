package org.jdc.template.model.database;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.config.BuildEnv;
import org.dbtools.android.domain.config.TestDatabaseConfig;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class TestMainDatabaseConfig extends TestDatabaseConfig {
    public static TestMainDatabaseConfig getInstance() {
        List<AndroidDatabase> databases = new ArrayList<>();
        databases.add(new AndroidDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME, BuildEnv.GRADLE.getTestDbDir() + DatabaseManagerConst.MAIN_DATABASE_NAME, DatabaseManager.MAIN_VERSION, DatabaseManager.MAIN_VIEWS_VERSION));

        return new TestMainDatabaseConfig(databases);
    }

    public TestMainDatabaseConfig(@Nonnull List<AndroidDatabase> androidDatabaseList) {
        super(androidDatabaseList);
    }
}

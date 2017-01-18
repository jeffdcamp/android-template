package org.jdc.template.model.database;

import android.app.Application;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidDatabaseBaseManager;
import org.dbtools.android.domain.config.DatabaseConfig;
import org.dbtools.android.domain.database.AndroidDatabaseWrapper;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.database.contentvalues.AndroidDBToolsContentValues;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import org.dbtools.android.domain.log.DBToolsLogger;
import org.jdc.template.log.DBToolsTimberLogger;

import java.util.Collections;

public class AppDatabaseConfig implements DatabaseConfig {
    private final Application application;

    public AppDatabaseConfig(Application application) {
        this.application = application;
    }

    @Override
    public DatabaseWrapper createNewDatabaseWrapper(AndroidDatabase androidDatabase) {
        return new AndroidDatabaseWrapper(androidDatabase.getPath()); // default built in Android
//        return new SQLiteDatabaseWrapper(androidDatabase.getPath()); // built version from sqlite.org
    }

    @Override
    public void identifyDatabases(AndroidDatabaseBaseManager databaseManager) {
        databaseManager.addDatabase(application, DatabaseManagerConst.MAIN_DATABASE_NAME, DatabaseManager.MAIN_VERSION, DatabaseManager.MAIN_VIEWS_VERSION);
        databaseManager.addDatabase(application, DatabaseManagerConst.OTHER_DATABASE_NAME, DatabaseManager.OTHER_VERSION, DatabaseManager.OTHER_VIEWS_VERSION);

        databaseManager.addAttachedDatabase(DatabaseManagerConst.ATTACHED_DATABASE_NAME, DatabaseManagerConst.MAIN_DATABASE_NAME, Collections.singletonList(DatabaseManagerConst.OTHER_DATABASE_NAME));
    }

    @Override
    public DBToolsContentValues createNewDBToolsContentValues() {
        return new AndroidDBToolsContentValues();
    }

    @Override
    public DBToolsLogger createNewDBToolsLogger() {
        return new DBToolsTimberLogger();
    }
}

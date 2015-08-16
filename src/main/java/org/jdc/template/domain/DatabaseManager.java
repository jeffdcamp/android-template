package org.jdc.template.domain;

import android.app.Application;
import android.util.Log;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.database.AndroidDatabaseWrapper;
import org.dbtools.android.domain.database.DatabaseWrapper;

import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DatabaseManager extends DatabaseBaseManager {

    @Inject
    Application application;
    public static final int MAIN_VERSION = 1;
    public static final int MAIN_VIEWS_VERSION = 3;

    public static final int OTHER_VERSION = 1;
    public static final int OTHER_VIEWS_VERSION = 1;

    @Inject
    public DatabaseManager() {
    }

    public void identifyDatabases() {
        addDatabase(application, MAIN_DATABASE_NAME, MAIN_VERSION, MAIN_VIEWS_VERSION);
        addDatabase(application, OTHER_DATABASE_NAME, OTHER_VERSION, OTHER_VIEWS_VERSION);

        addAttachedDatabase(ATTACHED_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Collections.singletonList(DatabaseManager.OTHER_DATABASE_NAME));
    }

    @Override
    public DatabaseWrapper createNewDatabaseWrapper(AndroidDatabase androidDatabase) {
        return new AndroidDatabaseWrapper(androidDatabase.getPath()); // default built in Android
//        return new SQLiteDatabaseWrapper(androidDatabase.getPath()); // built version from sqlite.org
    }

    public void onUpgrade(AndroidDatabase androidDatabase, int oldVersion, int newVersion) {
        String databaseName = androidDatabase.getName();
        Log.i(TAG, "Upgrading database [" + databaseName + "] from version " + oldVersion + " to " + newVersion);
        if (oldVersion < newVersion) {
            // todo implement database migration??
            deleteDatabase(androidDatabase);
            onCleanDatabase(androidDatabase);
        }
    }

    public void setContext(Application app) {
        this.application = app;
    }

    public void initDatabaseConnection() {
        Log.i(TAG, "Initializing Database connection: ");
        try {
            getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open database... attempting to recreate database", e);
            cleanAllDatabases();
            getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        }
    }
}
package org.company.project.domain;

import android.app.Application;
import android.util.Log;

import org.dbtools.android.domain.AndroidDatabase;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DatabaseManager extends DatabaseBaseManager {

    public static final String ATTACH_DATABASE_NAME = "attach"; // !!!! WARNING be SURE this matches the value in the schema.xml !!!!

    @Inject
    Application application;
    public static final int MAIN_VERSION = 1;
    public static final int MAIN_VIEWS_VERSION = 3;

    public static final int OTHER_VERSION = 1;
    public static final int OTHER_VIEWS_VERSION = 1;

    public void identifyDatabases() {
        addDatabase(application, MAIN_DATABASE_NAME, MAIN_VERSION, MAIN_VIEWS_VERSION);
        addDatabase(application, OTHER_DATABASE_NAME, OTHER_VERSION, OTHER_VIEWS_VERSION);
        addAttachedDatabase(ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME));
    }

    public void onUpgrade(AndroidDatabase androidDatabase, int oldVersion, int newVersion) {
        String databaseName = androidDatabase.getName();
        Log.i(TAG, "Upgrading database [" + databaseName + "] from version " + oldVersion + " to " + newVersion);
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
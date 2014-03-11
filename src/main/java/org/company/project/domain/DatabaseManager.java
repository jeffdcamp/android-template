package org.company.project.domain;

import android.content.Context;
import android.util.Log;

import org.company.project.ForApplication;
import org.company.project.MyApplication;
import org.dbtools.android.domain.AndroidDatabase;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This class helps open, create, and upgrade the database file.
 */

@Singleton
public class DatabaseManager extends DatabaseBaseManager {
    private static final String TAG = MyApplication.createTag(DatabaseManager.class);

    public static final int MAIN_DATABASE_VERSION = 1;
    public static final String MAIN_DATABASE_NAME = "main"; // !!!! WARNING be SURE this matches the value in the schema.xml !!!!

    public static final int OTHER_DATABASE_VERSION = 1;
    public static final String OTHER_DATABASE_NAME = "other"; // !!!! WARNING be SURE this matches the value in the schema.xml !!!!

    public static final String ATTACH_DATABASE_NAME = "attach"; // !!!! WARNING be SURE this matches the value in the schema.xml !!!!

    @ForApplication
    @Inject
    Context context;

    // ONLY needed if NOT using injection
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void identifyDatabases() {
        addDatabase(context, DatabaseManager.MAIN_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_VERSION);
        addDatabase(context, DatabaseManager.OTHER_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_VERSION);
        addAttachedDatabase(ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME));
    }

    @Override
    public void onUpgrade(AndroidDatabase androidDatabase, int oldVersion, int newVersion) {
        String databaseName = androidDatabase.getName();
        Log.i(TAG, "Upgrading database [" + databaseName + "] from version " + oldVersion + " to " + newVersion);

        // Wipe database version if it is different.
        switch (databaseName) {
            case MAIN_DATABASE_NAME:
                if (oldVersion != MAIN_DATABASE_VERSION) {
                    onCleanDatabase(androidDatabase);
                }
                break;
            case OTHER_DATABASE_NAME:
                if (oldVersion != OTHER_DATABASE_VERSION) {
                    onCleanDatabase(androidDatabase);
                }
                break;
        }
    }
}

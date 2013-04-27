package org.company.project.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.company.project.MyApplication;
import org.company.project.domain.individual.IndividualManager;
import org.company.project.domain.individualtype.IndividualType;
import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidDatabaseManager;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.File;


/**
 * This class helps open, create, and upgrade the database file.
 */

@Singleton
public class DatabaseManager extends AndroidDatabaseManager {
    private static final String TAG = MyApplication.createTag(DatabaseManager.class);

    private static final int DATABASE_VERSION = 1;
    public static final String MAIN_DATABASE_NAME = "main"; // !!!! WARNING be SURE this matches the value in the schema.xml !!!!

    @Inject
    private static Provider<Context> contextProvider;

    @Inject
    private IndividualManager individualManager;

    public DatabaseManager() {
        addDatabase(contextProvider.get(), MAIN_DATABASE_NAME, DATABASE_VERSION);
    }

    @Override
    public void onCreate(AndroidDatabase androidDatabase) {
        Log.i(TAG, "Creating database: " + androidDatabase.getName());
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();

        // use any record manager to begin/end transaction
        individualManager.beginTransaction(androidDatabase.getName());

        // Enum Tables
        BaseManager.createTable(database, IndividualType.CREATE_TABLE);

        // Regular Tables
        individualManager.createTable(database);

        // end transaction
        individualManager.endTransaction(androidDatabase.getName());
    }

    @Override
    public void onUpgrade(AndroidDatabase androidDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Wipe database version if it is different.
        if (oldVersion != DATABASE_VERSION) {
            onCleanDatabase(androidDatabase);
        }
    }

    @Override
    public void onCleanDatabase(AndroidDatabase androidDatabase) {
        Log.i(TAG, "Cleaning Database");
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();
        String databasePath = androidDatabase.getPath();
        database.close();

        Log.i(TAG, "Deleting database: [" + databasePath + "]");
        File databaseFile = new File(databasePath);
        if (databaseFile.exists() && !databaseFile.delete()) {
            String errorMessage = "FAILED to delete database: [" + databasePath + "]";
            Log.e(TAG, errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        connectDatabase(androidDatabase.getName(), false);  // do not update here, because it will cause a recursive call
    }
}

package org.company.project.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.company.project.ForApplication;
import org.company.project.MyApplication;
import org.company.project.domain.household.Household;
import org.company.project.domain.individual.Individual;
import org.company.project.domain.individualtype.IndividualType;
import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidDatabaseManager;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This class helps open, create, and upgrade the database file.
 */

@Singleton
public class DatabaseManager extends AndroidDatabaseManager {
    private static final String TAG = MyApplication.createTag(DatabaseManager.class);

    public static final int DATABASE_VERSION = 1;
    public static final String MAIN_DATABASE_NAME = "main"; // !!!! WARNING be SURE this matches the value in the schema.xml !!!!

    @ForApplication
    @Inject
    public Context context;

    @Override
    public void identifyDatabases() {
        addDatabase(context, DatabaseManager.MAIN_DATABASE_NAME, DatabaseManager.DATABASE_VERSION);
    }

    @Override
    public void onCreate(AndroidDatabase androidDatabase) {
        Log.i(TAG, "Creating database: " + androidDatabase.getName());
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();

        // use any record manager to begin/end transaction
        database.beginTransaction();

        // Enum Tables
        BaseManager.createTable(database, IndividualType.CREATE_TABLE);

        // Regular Tables
        BaseManager.createTable(database, Household.CREATE_TABLE);
        BaseManager.createTable(database, Individual.CREATE_TABLE);

        // end transaction
        database.setTransactionSuccessful();
        database.endTransaction();
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

    // ONLY needed if NOT using injection
    public void setContext(Context context) {
        this.context = context;
    }
}

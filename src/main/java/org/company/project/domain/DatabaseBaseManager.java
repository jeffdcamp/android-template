package org.company.project.domain;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.company.project.domain.household.Household;
import org.company.project.domain.individual.Individual;
import org.company.project.domain.individuallist.IndividualList;
import org.company.project.domain.individuallistitem.IndividualListItem;
import org.company.project.domain.individualtype.IndividualType;
import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidDatabaseManager;


/**
 * This class helps open, create, and upgrade the database file.
 */

public abstract class DatabaseBaseManager extends AndroidDatabaseManager {
    @Override
    public abstract void identifyDatabases();
    @Override
    public abstract void onUpgrade(AndroidDatabase androidDatabase, int oldVersion, int newVersion);

    @Override
    public void onCreate(AndroidDatabase androidDatabase) {
        Log.i(TAG, "Creating database: " + androidDatabase.getName());
        switch (androidDatabase.getName()) {
            case DatabaseManager.MAIN_DATABASE_NAME:
                createMainDatabase(androidDatabase);
                break;
            case DatabaseManager.OTHER_DATABASE_NAME:
                createOtherDatabase(androidDatabase);
                break;
        }
    }

    private void createMainDatabase(AndroidDatabase androidDatabase) {
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

    private void createOtherDatabase(AndroidDatabase androidDatabase) {
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();

        // use any record manager to begin/end transaction
        database.beginTransaction();

        // Enum Tables

        // Regular Tables
        BaseManager.createTable(database, IndividualList.CREATE_TABLE);
        BaseManager.createTable(database, IndividualListItem.CREATE_TABLE);

        // end transaction
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}

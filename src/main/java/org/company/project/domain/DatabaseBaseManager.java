/*
 * DatabaseBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 *
 */



package org.company.project.domain;

import android.util.Log;
import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidDatabaseManager;
import android.database.sqlite.SQLiteDatabase;
import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.individualtype.IndividualType;
import org.company.project.domain.household.Household;
import org.company.project.domain.individual.Individual;
import org.company.project.domain.individuallist.IndividualList;
import org.company.project.domain.individuallistitem.IndividualListItem;
import org.company.project.domain.phonelistview.PhoneListView;


@SuppressWarnings("all")
public abstract class DatabaseBaseManager extends AndroidDatabaseManager {

    public static final String MAIN_DATABASE_NAME = "main";
    public static final String OTHER_DATABASE_NAME = "other";

    public void createMainTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();
        database.beginTransaction();
        
        // Enum Tables
        AndroidBaseManager.createTable(database, IndividualType.CREATE_TABLE);
        
        // Tables
        AndroidBaseManager.createTable(database, Household.CREATE_TABLE);
        AndroidBaseManager.createTable(database, Individual.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void createOtherTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, IndividualList.CREATE_TABLE);
        AndroidBaseManager.createTable(database, IndividualListItem.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void onCreate(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        Log.i(TAG, "Creating database: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(MAIN_DATABASE_NAME)) {
            createMainTables(androidDatabase);
        }
        if (androidDatabase.getName().equals(OTHER_DATABASE_NAME)) {
            createOtherTables(androidDatabase);
        }
    }

    public void createMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.createTable(database, PhoneListView.CREATE_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void dropMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        SQLiteDatabase database = androidDatabase.getSqLiteDatabase();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.dropTable(database, PhoneListView.DROP_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void onCreateViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        Log.i(TAG, "Creating database views: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(MAIN_DATABASE_NAME)) {
            createMainViews(androidDatabase);
        }
    }

    public void onDropViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        Log.i(TAG, "Dropping database views: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(MAIN_DATABASE_NAME)) {
            dropMainViews(androidDatabase);
        }
    }


}
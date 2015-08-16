/*
 * DatabaseBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 *
 */



package org.jdc.template.domain;

import android.util.Log;
import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidBaseManager;
import org.dbtools.android.domain.AndroidDatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;


@SuppressWarnings("all")
public abstract class DatabaseBaseManager extends AndroidDatabaseManager {

    public static final String MAIN_DATABASE_NAME = "main";
    public static final String OTHER_DATABASE_NAME = "other";
    public static final String ATTACHED_DATABASE_NAME = "attached";

    public void createMainTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.individualtype.IndividualType.CREATE_TABLE);
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.household.Household.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.individual.Individual.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void createOtherTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.domain.other.individuallist.IndividualList.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.domain.other.individuallistitem.IndividualListItem.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void createAttachedTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        
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
        if (androidDatabase.getName().equals(ATTACHED_DATABASE_NAME)) {
            createAttachedTables(androidDatabase);
        }
    }

    public void createMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.phonelistview.PhoneListView.CREATE_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void dropMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.dropTable(database, org.jdc.template.domain.main.phonelistview.PhoneListView.DROP_VIEW);
        
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
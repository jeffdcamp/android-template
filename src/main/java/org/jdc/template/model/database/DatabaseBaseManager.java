/*
 * DatabaseBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 *
 */



package org.jdc.template.model.database;

import android.util.Log;
import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidBaseManager;
import org.dbtools.android.domain.AndroidDatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.jdc.template.model.database.main.household.HouseholdConst;
import org.jdc.template.model.database.main.individual.IndividualConst;
import org.jdc.template.model.database.main.individualtype.IndividualTypeConst;
import org.jdc.template.model.database.main.phonelistview.PhoneListView;
import org.jdc.template.model.database.other.individuallist.IndividualListConst;
import org.jdc.template.model.database.other.individuallistitem.IndividualListItemConst;


@SuppressWarnings("all")
public abstract class DatabaseBaseManager extends AndroidDatabaseManager {


    public void createMainTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        AndroidBaseManager.createTable(database, IndividualTypeConst.CREATE_TABLE);
        
        // Tables
        AndroidBaseManager.createTable(database, HouseholdConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, IndividualConst.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void createOtherTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, IndividualListConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, IndividualListItemConst.CREATE_TABLE);
        
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
        if (androidDatabase.getName().equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            createMainTables(androidDatabase);
        }
        if (androidDatabase.getName().equals(DatabaseManagerConst.OTHER_DATABASE_NAME)) {
            createOtherTables(androidDatabase);
        }
        if (androidDatabase.getName().equals(DatabaseManagerConst.ATTACHED_DATABASE_NAME)) {
            createAttachedTables(androidDatabase);
        }
    }

    public void createMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.createTable(database, PhoneListView.CREATE_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void dropMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.dropTable(database, PhoneListView.DROP_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void onCreateViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        Log.i(TAG, "Creating database views: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            createMainViews(androidDatabase);
        }
    }

    public void onDropViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        Log.i(TAG, "Dropping database views: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            dropMainViews(androidDatabase);
        }
    }


}
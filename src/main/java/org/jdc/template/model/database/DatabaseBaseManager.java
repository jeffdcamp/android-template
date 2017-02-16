/*
 * DatabaseBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 *
 */



package org.jdc.template.model.database;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.AndroidBaseManager;
import org.dbtools.android.domain.AndroidDatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.config.DatabaseConfig;


@SuppressWarnings("all")
public abstract class DatabaseBaseManager extends AndroidDatabaseManager {


    public DatabaseBaseManager(DatabaseConfig databaseConfig) {
        super(databaseConfig);
    }

    public void createMainTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.household.HouseholdConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.individual.IndividualConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.individualdata.IndividualDataConst.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void createOtherTables(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.other.individuallist.IndividualListConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.other.individuallistitem.IndividualListItemConst.CREATE_TABLE);
        
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
        getLogger().i(TAG, "Creating database: " + androidDatabase.getName());
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
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.phonelistview.PhoneListViewManager.CREATE_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void dropMainViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        DatabaseWrapper database = androidDatabase.getDatabaseWrapper();
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.dropTable(database, org.jdc.template.model.database.main.phonelistview.PhoneListViewManager.DROP_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void onCreateViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        getLogger().i(TAG, "Creating database views: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            createMainViews(androidDatabase);
        }
    }

    public void onDropViews(@javax.annotation.Nonnull AndroidDatabase androidDatabase) {
        getLogger().i(TAG, "Dropping database views: " + androidDatabase.getName());
        if (androidDatabase.getName().equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            dropMainViews(androidDatabase);
        }
    }


}
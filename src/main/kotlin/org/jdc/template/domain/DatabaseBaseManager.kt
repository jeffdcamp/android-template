/*
 * DatabaseBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain

import android.util.Log
import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.AndroidBaseManager
import org.dbtools.android.domain.AndroidDatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper


@SuppressWarnings("all")
abstract class DatabaseBaseManager : AndroidDatabaseManager() {


    fun createMainTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction();
        
        // Enum Tables
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.individualtype.IndividualTypeConst.CREATE_TABLE);
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.household.HouseholdConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.individual.IndividualConst.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    fun createOtherTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.domain.other.individuallist.IndividualListConst.CREATE_TABLE);
        AndroidBaseManager.createTable(database, org.jdc.template.domain.other.individuallistitem.IndividualListItemConst.CREATE_TABLE);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    fun createAttachedTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction();
        
        // Enum Tables
        
        // Tables
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    override fun onCreate(androidDatabase: AndroidDatabase) {
        Log.i(TAG, "Creating database: $androidDatabase.name");
        if (androidDatabase.name.equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            createMainTables(androidDatabase);
        }
        if (androidDatabase.name.equals(DatabaseManagerConst.OTHER_DATABASE_NAME)) {
            createOtherTables(androidDatabase);
        }
        if (androidDatabase.name.equals(DatabaseManagerConst.ATTACHED_DATABASE_NAME)) {
            createAttachedTables(androidDatabase);
        }
    }

    fun createMainViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.createTable(database, org.jdc.template.domain.main.phonelistview.PhoneListView.CREATE_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    fun dropMainViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction();
        
        // Views
        AndroidBaseManager.dropTable(database, org.jdc.template.domain.main.phonelistview.PhoneListView.DROP_VIEW);
        
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    override fun onCreateViews(androidDatabase: AndroidDatabase) {
        Log.i(TAG, "Creating database views: $androidDatabase.name")
        if (androidDatabase.name.equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            createMainViews(androidDatabase)
        }
    }

    override fun onDropViews(androidDatabase: AndroidDatabase) {
        Log.i(TAG, "Dropping database views: $androidDatabase.name")
        if (androidDatabase.name.equals(DatabaseManagerConst.MAIN_DATABASE_NAME)) {
            dropMainViews(androidDatabase);
        }
    }


}
/*
 * DatabaseBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database

import org.dbtools.android.domain.AndroidBaseManager
import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.AndroidDatabaseManager
import org.dbtools.android.domain.config.DatabaseConfig


@Suppress("unused", "ConvertSecondaryConstructorToPrimary", "RemoveEmptySecondaryConstructorBody")
@SuppressWarnings("all")
abstract class DatabaseBaseManager : AndroidDatabaseManager {


    constructor(databaseConfig: DatabaseConfig) : super(databaseConfig) {
    }

    open fun createMainTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.individualtype.IndividualTypeConst.CREATE_TABLE)
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.household.HouseholdConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.individualdata.IndividualDataConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.individual.IndividualConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createOtherTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.other.individuallist.IndividualListConst.CREATE_TABLE)
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.other.individuallistitem.IndividualListItemConst.CREATE_TABLE)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun createAttachedTables(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Enum Tables
        
        // Tables
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    override fun onCreate(androidDatabase: AndroidDatabase) {
        logger.i(TAG, "Creating database: ${androidDatabase.name}")
        if (androidDatabase.name == DatabaseManagerConst.MAIN_DATABASE_NAME) {
            createMainTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.OTHER_DATABASE_NAME) {
            createOtherTables(androidDatabase)
        }
        if (androidDatabase.name == DatabaseManagerConst.ATTACHED_DATABASE_NAME) {
            createAttachedTables(androidDatabase)
        }
    }

    open fun createMainViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.createTable(database, org.jdc.template.model.database.main.phonelistview.PhoneListView.CREATE_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    open fun dropMainViews(androidDatabase: AndroidDatabase) {
        val database = androidDatabase.databaseWrapper
        database.beginTransaction()
        
        // Views
        AndroidBaseManager.dropTable(database, org.jdc.template.model.database.main.phonelistview.PhoneListView.DROP_VIEW)
        
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    override fun onCreateViews(androidDatabase: AndroidDatabase) {
        logger.i(TAG, "Creating database views: ${androidDatabase.name}")
        if (androidDatabase.name == DatabaseManagerConst.MAIN_DATABASE_NAME) {
            createMainViews(androidDatabase)
        }
    }

    override fun onDropViews(androidDatabase: AndroidDatabase) {
        logger.i(TAG, "Dropping database views: ${androidDatabase.name}")
        if (androidDatabase.name == DatabaseManagerConst.MAIN_DATABASE_NAME) {
            dropMainViews(androidDatabase)
        }
    }


}
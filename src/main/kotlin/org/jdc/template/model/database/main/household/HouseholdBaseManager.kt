/*
 * HouseholdBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.household

import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable
import org.dbtools.android.domain.database.DatabaseWrapper
import org.jdc.template.model.database.DatabaseManager


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class HouseholdBaseManager : RxKotlinAndroidBaseManagerWritable<Household> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return HouseholdConst.DATABASE
    }

    override fun newRecord() : Household {
        return Household()
    }

    override fun getTableName() : String {
        return HouseholdConst.TABLE
    }

    override fun getAllColumns() : Array<String> {
        return HouseholdConst.ALL_COLUMNS
    }

    override fun getReadableDatabase(@javax.annotation.Nonnull databaseName: String) : DatabaseWrapper<*, *> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    fun getReadableDatabase() : DatabaseWrapper<*, *> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    override fun getWritableDatabase(@javax.annotation.Nonnull databaseName: String) : DatabaseWrapper<*, *> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    fun getWritableDatabase() : DatabaseWrapper<*, *> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    override fun getAndroidDatabase(@javax.annotation.Nonnull databaseName: String) : org.dbtools.android.domain.AndroidDatabase? {
        return databaseManager.getDatabase(databaseName)
    }

    override fun getDatabaseConfig() : org.dbtools.android.domain.config.DatabaseConfig {
        return databaseManager.databaseConfig
    }

    override fun getPrimaryKey() : String {
        return HouseholdConst.PRIMARY_KEY_COLUMN
    }

    override fun getDropSql() : String {
        return HouseholdConst.DROP_TABLE
    }

    override fun getCreateSql() : String {
        return HouseholdConst.CREATE_TABLE
    }

    override fun getInsertSql() : String {
        return HouseholdConst.INSERT_STATEMENT
    }

    override fun getUpdateSql() : String {
        return HouseholdConst.UPDATE_STATEMENT
    }


}
/*
 * IndividualDataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individualdata

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class IndividualDataBaseManager : RxKotlinAndroidBaseManagerWritable<IndividualData> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return IndividualDataConst.DATABASE
    }

    override fun newRecord() : IndividualData {
        return IndividualData()
    }

    override fun getTableName() : String {
        return IndividualDataConst.TABLE
    }

    override fun getAllColumns() : Array<String> {
        return IndividualDataConst.ALL_COLUMNS
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
        return "NO_PRIMARY_KEY"
    }

    override fun getDropSql() : String {
        return IndividualDataConst.DROP_TABLE
    }

    override fun getCreateSql() : String {
        return IndividualDataConst.CREATE_TABLE
    }

    override fun getInsertSql() : String {
        return IndividualDataConst.INSERT_STATEMENT
    }

    override fun getUpdateSql() : String {
        return IndividualDataConst.UPDATE_STATEMENT
    }


}
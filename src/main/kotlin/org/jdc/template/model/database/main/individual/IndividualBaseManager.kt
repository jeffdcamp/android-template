/*
 * IndividualBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individual

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class IndividualBaseManager : RxKotlinAndroidBaseManagerWritable<Individual> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return IndividualConst.DATABASE
    }

    override fun newRecord() : Individual {
        return Individual()
    }

    override fun getTableName() : String {
        return IndividualConst.TABLE
    }

    override fun getAllColumns() : Array<String> {
        return IndividualConst.ALL_COLUMNS
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
        return IndividualConst.PRIMARY_KEY_COLUMN
    }

    override fun getDropSql() : String {
        return IndividualConst.DROP_TABLE
    }

    override fun getCreateSql() : String {
        return IndividualConst.CREATE_TABLE
    }

    override fun getInsertSql() : String {
        return IndividualConst.INSERT_STATEMENT
    }

    override fun getUpdateSql() : String {
        return IndividualConst.UPDATE_STATEMENT
    }


}
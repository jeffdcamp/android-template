/*
 * IndividualBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.main.individual

import org.jdc.template.domain.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@SuppressWarnings("all")
abstract class IndividualBaseManager : RxKotlinAndroidBaseManagerWritable<Individual> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName(): String {
        return IndividualConst.DATABASE
    }

    override fun newRecord(): Individual {
        return Individual()
    }

    override fun getTableName(): String {
        return IndividualConst.TABLE
    }

    override fun getAllColumns(): Array<String> {
        return IndividualConst.ALL_COLUMNS
    }

    override fun getReadableDatabase(@javax.annotation.Nonnull databaseName: String): DatabaseWrapper<*> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    fun getReadableDatabase(): DatabaseWrapper<*> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    override fun getWritableDatabase(@javax.annotation.Nonnull databaseName: String): DatabaseWrapper<*> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    fun getWritableDatabase(): DatabaseWrapper<*> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    override fun getAndroidDatabase(@javax.annotation.Nonnull databaseName: String): org.dbtools.android.domain.AndroidDatabase? {
        return databaseManager.getDatabase(databaseName)
    }

    override fun getPrimaryKey(): String {
        return IndividualConst.PRIMARY_KEY_COLUMN
    }

    override fun getDropSql(): String {
        return IndividualConst.DROP_TABLE
    }

    override fun getCreateSql(): String {
        return IndividualConst.CREATE_TABLE
    }


}
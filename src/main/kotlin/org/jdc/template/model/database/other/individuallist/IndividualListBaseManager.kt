/*
 * IndividualListBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.other.individuallist

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@SuppressWarnings("all")
abstract class IndividualListBaseManager : RxKotlinAndroidBaseManagerWritable<IndividualList> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName(): String {
        return IndividualListConst.DATABASE
    }

    override fun newRecord(): IndividualList {
        return IndividualList()
    }

    override fun getTableName(): String {
        return IndividualListConst.TABLE
    }

    override fun getAllColumns(): Array<String> {
        return IndividualListConst.ALL_COLUMNS
    }

    override fun getReadableDatabase(@javax.annotation.Nonnull databaseName: String): DatabaseWrapper<*, *> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    fun getReadableDatabase(): DatabaseWrapper<*, *> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    override fun getWritableDatabase(@javax.annotation.Nonnull databaseName: String): DatabaseWrapper<*, *> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    fun getWritableDatabase(): DatabaseWrapper<*, *> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    override fun getAndroidDatabase(@javax.annotation.Nonnull databaseName: String): org.dbtools.android.domain.AndroidDatabase? {
        return databaseManager.getDatabase(databaseName)
    }

    override fun getPrimaryKey(): String {
        return IndividualListConst.PRIMARY_KEY_COLUMN
    }

    override fun getDropSql(): String {
        return IndividualListConst.DROP_TABLE
    }

    override fun getCreateSql(): String {
        return IndividualListConst.CREATE_TABLE
    }


}
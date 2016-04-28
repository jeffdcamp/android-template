/*
 * IndividualListItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.other.individuallistitem

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@SuppressWarnings("all")
abstract class IndividualListItemBaseManager : RxKotlinAndroidBaseManagerWritable<IndividualListItem> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName(): String {
        return IndividualListItemConst.DATABASE
    }

    override fun newRecord(): IndividualListItem {
        return IndividualListItem()
    }

    override fun getTableName(): String {
        return IndividualListItemConst.TABLE
    }

    override fun getAllColumns(): Array<String> {
        return IndividualListItemConst.ALL_COLUMNS
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
        return IndividualListItemConst.PRIMARY_KEY_COLUMN
    }

    override fun getDropSql(): String {
        return IndividualListItemConst.DROP_TABLE
    }

    override fun getCreateSql(): String {
        return IndividualListItemConst.CREATE_TABLE
    }

    override fun getInsertSql(): String {
        return IndividualListItemConst.INSERT_STATEMENT
    }

    override fun getUpdateSql(): String {
        return IndividualListItemConst.UPDATE_STATEMENT
    }


}
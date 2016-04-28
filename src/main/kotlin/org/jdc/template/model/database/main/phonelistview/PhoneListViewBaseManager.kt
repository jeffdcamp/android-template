/*
 * PhoneListViewBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.phonelistview

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@SuppressWarnings("all")
abstract class PhoneListViewBaseManager : RxKotlinAndroidBaseManagerReadOnly<PhoneListView> {

     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName(): String {
        return PhoneListViewConst.DATABASE
    }

    override fun newRecord(): PhoneListView {
        return PhoneListView()
    }

    override fun getTableName(): String {
        return PhoneListViewConst.TABLE
    }

    override fun getAllColumns(): Array<String> {
        return PhoneListViewConst.ALL_COLUMNS
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
        return ""
    }

    override fun getDropSql(): String {
        return PhoneListView.DROP_VIEW
    }

    override fun getCreateSql(): String {
        return PhoneListView.CREATE_VIEW
    }

    override fun getInsertSql(): String {
        return ""
    }

    override fun getUpdateSql(): String {
        return ""
    }


}
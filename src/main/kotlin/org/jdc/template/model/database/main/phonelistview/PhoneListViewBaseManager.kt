/*
 * PhoneListViewBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.phonelistview

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class PhoneListViewBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<PhoneListView>(databaseManager) {

     override val allColumns: Array<String> = PhoneListViewConst.ALL_COLUMNS
     override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
     override val dropSql = PhoneListViewManager.DROP_VIEW
     override val createSql = PhoneListViewManager.CREATE_VIEW
     override val insertSql = ""
     override val updateSql = ""

    override fun getDatabaseName() : String {
        return PhoneListViewConst.DATABASE
    }

    override fun newRecord() : PhoneListView {
        return PhoneListView()
    }

    override fun getTableName() : String {
        return PhoneListViewConst.TABLE
    }


}
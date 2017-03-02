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
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.dbtools.android.domain.AndroidBaseRecord


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class PhoneListViewBaseManager  : RxKotlinAndroidBaseManagerReadOnly<PhoneListView> {

     override val allColumns: Array<String> = PhoneListViewConst.ALL_COLUMNS
     override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
     override val dropSql = PhoneListViewManager.DROP_VIEW
     override val createSql = PhoneListViewManager.CREATE_VIEW
     override val insertSql = ""
     override val updateSql = ""
     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return PhoneListViewConst.DATABASE
    }

    override fun newRecord() : PhoneListView {
        return PhoneListView()
    }

    override fun getTableName() : String {
        return PhoneListViewConst.TABLE
    }

    override fun getReadableDatabase(@javax.annotation.Nonnull databaseName: String) : DatabaseWrapper<in AndroidBaseRecord, in DBToolsContentValues<*>> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    override fun getWritableDatabase(@javax.annotation.Nonnull databaseName: String) : DatabaseWrapper<in AndroidBaseRecord, in DBToolsContentValues<*>> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    override fun getAndroidDatabase(@javax.annotation.Nonnull databaseName: String) : org.dbtools.android.domain.AndroidDatabase? {
        return databaseManager.getDatabase(databaseName)
    }

    override fun getDatabaseConfig() : org.dbtools.android.domain.config.DatabaseConfig {
        return databaseManager.databaseConfig
    }


}
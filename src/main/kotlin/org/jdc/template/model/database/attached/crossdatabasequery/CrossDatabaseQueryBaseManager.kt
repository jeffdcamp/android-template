/*
 * CrossDatabaseQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.attached.crossdatabasequery

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class CrossDatabaseQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<CrossDatabaseQuery>(databaseManager) {

     override val allColumns: Array<String> = CrossDatabaseQueryConst.ALL_COLUMNS
     override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
     override val dropSql = ""
     override val createSql = ""
     override val insertSql = ""
     override val updateSql = ""

    override fun getDatabaseName() : String {
        return CrossDatabaseQueryConst.DATABASE
    }

    override fun newRecord() : CrossDatabaseQuery {
        return CrossDatabaseQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}
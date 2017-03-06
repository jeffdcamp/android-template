/*
 * IndividualQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individualquery

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class IndividualQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<IndividualQuery>(databaseManager) {

     override val allColumns: Array<String> = IndividualQueryConst.ALL_COLUMNS
     override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
     override val dropSql = ""
     override val createSql = ""
     override val insertSql = ""
     override val updateSql = ""

    override fun getDatabaseName() : String {
        return IndividualQueryConst.DATABASE
    }

    override fun newRecord() : IndividualQuery {
        return IndividualQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}
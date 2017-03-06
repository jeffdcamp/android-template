/*
 * IndividualBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individual

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class IndividualBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Individual>(databaseManager) {

     override val allColumns: Array<String> = IndividualConst.ALL_COLUMNS
     override val primaryKey = IndividualConst.PRIMARY_KEY_COLUMN
     override val dropSql = IndividualConst.DROP_TABLE
     override val createSql = IndividualConst.CREATE_TABLE
     override val insertSql = IndividualConst.INSERT_STATEMENT
     override val updateSql = IndividualConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return IndividualConst.DATABASE
    }

    override fun newRecord() : Individual {
        return Individual()
    }

    override fun getTableName() : String {
        return IndividualConst.TABLE
    }


}
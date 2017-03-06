/*
 * IndividualListBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.other.individuallist

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class IndividualListBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<IndividualList>(databaseManager) {

     override val allColumns: Array<String> = IndividualListConst.ALL_COLUMNS
     override val primaryKey = IndividualListConst.PRIMARY_KEY_COLUMN
     override val dropSql = IndividualListConst.DROP_TABLE
     override val createSql = IndividualListConst.CREATE_TABLE
     override val insertSql = IndividualListConst.INSERT_STATEMENT
     override val updateSql = IndividualListConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return IndividualListConst.DATABASE
    }

    override fun newRecord() : IndividualList {
        return IndividualList()
    }

    override fun getTableName() : String {
        return IndividualListConst.TABLE
    }


}
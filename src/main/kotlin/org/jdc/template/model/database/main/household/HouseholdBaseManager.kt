/*
 * HouseholdBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.household

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class HouseholdBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Household>(databaseManager) {

     override val allColumns: Array<String> = HouseholdConst.ALL_COLUMNS
     override val primaryKey = HouseholdConst.PRIMARY_KEY_COLUMN
     override val dropSql = HouseholdConst.DROP_TABLE
     override val createSql = HouseholdConst.CREATE_TABLE
     override val insertSql = HouseholdConst.INSERT_STATEMENT
     override val updateSql = HouseholdConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return HouseholdConst.DATABASE
    }

    override fun newRecord() : Household {
        return Household()
    }

    override fun getTableName() : String {
        return HouseholdConst.TABLE
    }


}
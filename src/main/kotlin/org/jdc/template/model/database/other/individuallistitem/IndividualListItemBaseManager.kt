/*
 * IndividualListItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.other.individuallistitem

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class IndividualListItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<IndividualListItem>(databaseManager) {

     override val allColumns: Array<String> = IndividualListItemConst.ALL_COLUMNS
     override val primaryKey = IndividualListItemConst.PRIMARY_KEY_COLUMN
     override val dropSql = IndividualListItemConst.DROP_TABLE
     override val createSql = IndividualListItemConst.CREATE_TABLE
     override val insertSql = IndividualListItemConst.INSERT_STATEMENT
     override val updateSql = IndividualListItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return IndividualListItemConst.DATABASE
    }

    override fun newRecord() : IndividualListItem {
        return IndividualListItem()
    }

    override fun getTableName() : String {
        return IndividualListItemConst.TABLE
    }


}
/*
 * IndividualListItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallistitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody")
@SuppressWarnings("all")
abstract class IndividualListItemBaseRecord : AndroidBaseRecord {

     open var id: Long = 0
     open var listId: Long = 0
     open var individualId: Long = 0

    constructor(record: IndividualListItem) {
        this.listId = record.listId
        this.individualId = record.individualId
    }

    constructor() {
    }

    override fun getIdColumnName() : String {
        return IndividualListItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return IndividualListItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return IndividualListItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(IndividualListItemConst.C_LIST_ID, listId)
        values.put(IndividualListItemConst.C_INDIVIDUAL_ID, individualId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            listId,
            individualId)
    }

    open fun copy() : IndividualListItem {
        val copy = IndividualListItem()
        copy.id = id
        copy.listId = listId
        copy.individualId = individualId
        return copy
    }

    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, listId)
        statement.bindLong(2, individualId)
    }

    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, listId)
        statement.bindLong(2, individualId)
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        listId = values.getAsLong(IndividualListItemConst.C_LIST_ID)
        individualId = values.getAsLong(IndividualListItemConst.C_INDIVIDUAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_ID))
        listId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_LIST_ID))
        individualId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_INDIVIDUAL_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
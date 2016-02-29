/*
 * IndividualListItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.other.individuallistitem

import org.dbtools.android.domain.AndroidBaseRecord
import android.content.ContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class IndividualListItemBaseRecord : AndroidBaseRecord() {

     var id: Long = 0
     var listId: Long = 0
     var individualId: Long = 0

    override fun getIdColumnName(): String {
        return IndividualListItemConst.C_ID
    }

    override fun getPrimaryKeyId(): Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns(): Array<String> {
        return IndividualListItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return IndividualListItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(): ContentValues {
        val values = ContentValues()
        values.put(IndividualListItemConst.C_LIST_ID, listId)
        values.put(IndividualListItemConst.C_INDIVIDUAL_ID, individualId)
        return values
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            listId,
            individualId)
    }

    fun setContent(values: ContentValues) {
        listId = values.getAsLong(IndividualListItemConst.C_LIST_ID)
        individualId = values.getAsLong(IndividualListItemConst.C_INDIVIDUAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_ID))
        listId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_LIST_ID))
        individualId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_INDIVIDUAL_ID))
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
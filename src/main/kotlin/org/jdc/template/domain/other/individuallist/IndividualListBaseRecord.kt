/*
 * IndividualListBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.other.individuallist

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.DBToolsDateFormatter
import android.content.ContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class IndividualListBaseRecord : AndroidBaseRecord() {

     var id: Long = 0
     var name: String = ""

    override fun getIdColumnName(): String {
        return IndividualListConst.C_ID
    }

    override fun getPrimaryKeyId(): Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns(): Array<String> {
        return IndividualListConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return IndividualListConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(): ContentValues {
        val values = ContentValues()
        values.put(IndividualListConst.C_NAME, name)
        return values
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            name)
    }

    fun setContent(values: ContentValues) {
        name = values.getAsString(IndividualListConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(IndividualListConst.C_NAME))
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
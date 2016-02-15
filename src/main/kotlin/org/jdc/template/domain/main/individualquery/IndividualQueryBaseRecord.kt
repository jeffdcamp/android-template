/*
 * IndividualQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individualquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.DBToolsDateFormatter
import android.content.ContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class IndividualQueryBaseRecord : AndroidBaseRecord() {

     var id: Long = 0
     var name: String = ""

    override fun getIdColumnName(): String {
        return ""
    }

    override fun getPrimaryKeyId(): Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns(): Array<String> {
        return IndividualQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return IndividualQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(): ContentValues {
        val values = ContentValues()
        values.put(IndividualQueryConst.C_ID, id)
        values.put(IndividualQueryConst.C_NAME, name)
        return values
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            name)
    }

    fun setContent(values: ContentValues) {
        id = values.getAsLong(IndividualQueryConst.C_ID)
        name = values.getAsString(IndividualQueryConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualQueryConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(IndividualQueryConst.C_NAME))
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
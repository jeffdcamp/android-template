/*
 * PhoneListViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.phonelistview

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class PhoneListViewBaseRecord : AndroidBaseRecord() {

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
        return PhoneListViewConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return PhoneListViewConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(PhoneListViewConst.C_ID, id)
        values.put(PhoneListViewConst.C_NAME, name)
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            name)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(PhoneListViewConst.C_ID)
        name = values.getAsString(PhoneListViewConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(PhoneListViewConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(PhoneListViewConst.C_NAME))
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
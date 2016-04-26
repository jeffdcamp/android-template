/*
 * CrossDatabaseQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.attached.crossdatabasequery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class CrossDatabaseQueryBaseRecord : AndroidBaseRecord() {

     var id: Long = 0
     var name: String = ""
     var type: LocationType = LocationType.HOME

    override fun getIdColumnName(): String {
        return ""
    }

    override fun getPrimaryKeyId(): Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns(): Array<String> {
        return CrossDatabaseQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return CrossDatabaseQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(CrossDatabaseQueryConst.C_ID, id)
        values.put(CrossDatabaseQueryConst.C_NAME, name)
        values.put(CrossDatabaseQueryConst.C_TYPE, type.ordinal)
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            name,
            type.ordinal)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(CrossDatabaseQueryConst.C_ID)
        name = values.getAsString(CrossDatabaseQueryConst.C_NAME)
        type = LocationType.values()[values.getAsInteger(CrossDatabaseQueryConst.C_TYPE)]
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_NAME))
        type = LocationType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_TYPE))]
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
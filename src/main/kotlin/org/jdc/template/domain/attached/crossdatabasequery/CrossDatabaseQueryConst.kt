/*
 * CrossDatabaseQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.attached.crossdatabasequery

import org.dbtools.android.domain.DBToolsDateFormatter
import android.database.Cursor


@SuppressWarnings("all")
object CrossDatabaseQueryConst {

    const val DATABASE = "attached"
    const val C_ID = "_id"
    const val FULL_C_ID = "CROSS_DATABASE_QUERY._id"
    const val C_NAME = "NAME"
    const val FULL_C_NAME = "CROSS_DATABASE_QUERY.NAME"
    const val C_TYPE = "TYPE"
    const val FULL_C_TYPE = "CROSS_DATABASE_QUERY.TYPE"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME,
        C_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME,
        FULL_C_TYPE)

    fun getId(cursor: Cursor): Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getType(cursor: Cursor): LocationType {
        return LocationType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE))]
    }


}
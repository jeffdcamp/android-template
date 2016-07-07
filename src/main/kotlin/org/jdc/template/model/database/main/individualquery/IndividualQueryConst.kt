/*
 * IndividualQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individualquery

import android.database.Cursor


@SuppressWarnings("all")
object IndividualQueryConst {

    const val DATABASE = "main"
    const val C_ID = "ID"
    const val FULL_C_ID = "INDIVIDUAL_QUERY.ID"
    const val C_NAME = "NAME"
    const val FULL_C_NAME = "INDIVIDUAL_QUERY.NAME"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}
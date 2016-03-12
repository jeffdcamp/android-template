/*
 * HouseholdBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.household

import android.database.Cursor


@SuppressWarnings("all")
object HouseholdConst {

    const val DATABASE = "main"
    const val TABLE = "HOUSEHOLD"
    const val FULL_TABLE = "main.HOUSEHOLD"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "HOUSEHOLD._id"
    const val C_NAME = "NAME"
    const val FULL_C_NAME = "HOUSEHOLD.NAME"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS HOUSEHOLD (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS HOUSEHOLD;"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME)

    fun getId(cursor: Cursor): Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}
/*
 * IndividualListBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallist

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object IndividualListConst  {

     const val DATABASE = "other"
     const val TABLE = "INDIVIDUAL_LIST"
     const val FULL_TABLE = "other.INDIVIDUAL_LIST"
     const val PRIMARY_KEY_COLUMN = "_id"
     const val C_ID = "_id"
     const val FULL_C_ID = "INDIVIDUAL_LIST._id"
     const val C_NAME = "NAME"
     const val FULL_C_NAME = "INDIVIDUAL_LIST.NAME"
     const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
     const val DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST;"
     const val INSERT_STATEMENT = "INSERT INTO INDIVIDUAL_LIST (NAME) VALUES (?)"
     const val UPDATE_STATEMENT = "UPDATE INDIVIDUAL_LIST SET NAME=? WHERE _id = ?"
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
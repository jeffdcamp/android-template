/*
 * IndividualDataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individualdata

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object IndividualDataConst  {

     const val DATABASE = "main"
     const val TABLE = "INDIVIDUAL_DATA"
     const val FULL_TABLE = "main.INDIVIDUAL_DATA"
     const val C_EXTERNAL_ID = "EXTERNAL_ID"
     const val FULL_C_EXTERNAL_ID = "INDIVIDUAL_DATA.EXTERNAL_ID"
     const val C_TYPE_ID = "TYPE_ID"
     const val FULL_C_TYPE_ID = "INDIVIDUAL_DATA.TYPE_ID"
     const val C_NAME = "NAME"
     const val FULL_C_NAME = "INDIVIDUAL_DATA.NAME"
     const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_DATA (" + 
        "EXTERNAL_ID INTEGER NOT NULL," + 
        "TYPE_ID INTEGER NOT NULL," + 
        "NAME TEXT NOT NULL," + 
        "UNIQUE(EXTERNAL_ID, TYPE_ID) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS INDIVIDUAL_DATAEXTERNAL_ID_IDX ON INDIVIDUAL_DATA (EXTERNAL_ID);" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS INDIVIDUAL_DATATYPE_ID_IDX ON INDIVIDUAL_DATA (TYPE_ID);" + 
        "" + 
        ""
     const val DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_DATA;"
     const val INSERT_STATEMENT = "INSERT INTO INDIVIDUAL_DATA (EXTERNAL_ID,TYPE_ID,NAME) VALUES (?,?,?)"
     const val UPDATE_STATEMENT = "UPDATE INDIVIDUAL_DATA SET EXTERNAL_ID=?, TYPE_ID=?, NAME=? WHERE  = ?"
     val ALL_COLUMNS = arrayOf(
        C_EXTERNAL_ID,
        C_TYPE_ID,
        C_NAME)
     val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_EXTERNAL_ID,
        FULL_C_TYPE_ID,
        FULL_C_NAME)

    fun getExternalId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID))
    }

    fun getTypeId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}
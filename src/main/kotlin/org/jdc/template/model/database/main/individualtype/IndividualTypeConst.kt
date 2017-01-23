/*
 * IndividualType.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individualtype

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody")
@SuppressWarnings("all")
object IndividualTypeConst {

    const val DATABASE = "main"
    const val TABLE = "INDIVIDUAL_TYPE"
    const val FULL_TABLE = "main.INDIVIDUAL_TYPE"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "INDIVIDUAL_TYPE._id"
    const val C_NAME = "NAME"
    const val FULL_C_NAME = "INDIVIDUAL_TYPE.NAME"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_TYPE (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL," + 
        "UNIQUE(NAME)" + 
        ");" + 
        "" + 
        "" + 
        "INSERT INTO INDIVIDUAL_TYPE (_id, NAME) VALUES (0, 'Head');" + 
        "INSERT INTO INDIVIDUAL_TYPE (_id, NAME) VALUES (1, 'Spouse');" + 
        "INSERT INTO INDIVIDUAL_TYPE (_id, NAME) VALUES (2, 'Child');" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_TYPE;"
    const val INSERT_STATEMENT = "INSERT INTO INDIVIDUAL_TYPE (NAME) VALUES (?)"
    const val UPDATE_STATEMENT = "UPDATE INDIVIDUAL_TYPE SET NAME=? WHERE _id = ?"

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}
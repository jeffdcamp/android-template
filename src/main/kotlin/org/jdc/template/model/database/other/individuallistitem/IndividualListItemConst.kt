/*
 * IndividualListItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallistitem

import android.database.Cursor


@SuppressWarnings("all")
object IndividualListItemConst {

    const val DATABASE = "other"
    const val TABLE = "INDIVIDUAL_LIST_ITEM"
    const val FULL_TABLE = "other.INDIVIDUAL_LIST_ITEM"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "INDIVIDUAL_LIST_ITEM._id"
    const val C_LIST_ID = "LIST_ID"
    const val FULL_C_LIST_ID = "INDIVIDUAL_LIST_ITEM.LIST_ID"
    const val C_INDIVIDUAL_ID = "INDIVIDUAL_ID"
    const val FULL_C_INDIVIDUAL_ID = "INDIVIDUAL_LIST_ITEM.INDIVIDUAL_ID"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST_ITEM (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "LIST_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_ID INTEGER NOT NULL," + 
        "FOREIGN KEY (LIST_ID) REFERENCES INDIVIDUAL_LIST (_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST_ITEM;"
    const val INSERT_STATEMENT = "INSERT INTO INDIVIDUAL_LIST_ITEM (LIST_ID,INDIVIDUAL_ID) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE INDIVIDUAL_LIST_ITEM SET LIST_ID=?, INDIVIDUAL_ID=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_LIST_ID,
        C_INDIVIDUAL_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_LIST_ID,
        FULL_C_INDIVIDUAL_ID)

    fun getId(cursor: Cursor): Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getListId(cursor: Cursor): Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LIST_ID))
    }

    fun getIndividualId(cursor: Cursor): Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_ID))
    }


}
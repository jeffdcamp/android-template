/*
 * PhoneListViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.phonelistview

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody")
@SuppressWarnings("all")
object PhoneListViewConst {

    const val DATABASE = "main"
    const val TABLE = "PHONE_LIST_VIEW"
    const val FULL_TABLE = "main.PHONE_LIST_VIEW"
    const val C_ID = "_id"
    const val FULL_C_ID = "PHONE_LIST_VIEW._id"
    const val C_NAME = "NAME"
    const val FULL_C_NAME = "PHONE_LIST_VIEW.NAME"
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
/*
 * PhoneListViewBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.phonelistview;

import android.database.Cursor;


@SuppressWarnings("all")
public class PhoneListViewConst {

    public static final String DATABASE = "main";
    public static final String TABLE = "PHONE_LIST_VIEW";
    public static final String FULL_TABLE = "main.PHONE_LIST_VIEW";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "PHONE_LIST_VIEW._id";
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "PHONE_LIST_VIEW.NAME";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_NAME};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_NAME};

    public PhoneListViewConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
    }


}
/*
 * HouseholdBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.household;

import android.database.Cursor;


@SuppressWarnings("all")
public class HouseholdConst {

    public static final String DATABASE = "main";
    public static final String TABLE = "HOUSEHOLD";
    public static final String FULL_TABLE = "main.HOUSEHOLD";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "HOUSEHOLD._id";
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "HOUSEHOLD.NAME";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS HOUSEHOLD (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS HOUSEHOLD;";
    public static final String INSERT_STATEMENT = "INSERT INTO HOUSEHOLD (NAME) VALUES (?)";
    public static final String UPDATE_STATEMENT = "UPDATE HOUSEHOLD SET NAME=? WHERE _id = ?";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_NAME};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_NAME};

    public HouseholdConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
    }


}
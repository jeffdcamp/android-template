/*
 * IndividualListBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallist;

import android.database.Cursor;


@SuppressWarnings("all")
public class IndividualListConst {

    public static final String DATABASE = "other";
    public static final String TABLE = "INDIVIDUAL_LIST";
    public static final String FULL_TABLE = "other.INDIVIDUAL_LIST";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL_LIST._id";
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "INDIVIDUAL_LIST.NAME";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST;";
    public static final String INSERT_STATEMENT = "INSERT INTO INDIVIDUAL_LIST (NAME) VALUES (?)";
    public static final String UPDATE_STATEMENT = "UPDATE INDIVIDUAL_LIST SET NAME=? WHERE _id = ?";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_NAME};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_NAME};

    public IndividualListConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
    }


}
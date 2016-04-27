/*
 * IndividualListItemBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallistitem;

import android.database.Cursor;


@SuppressWarnings("all")
public class IndividualListItemConst {

    public static final String DATABASE = "other";
    public static final String TABLE = "INDIVIDUAL_LIST_ITEM";
    public static final String FULL_TABLE = "other.INDIVIDUAL_LIST_ITEM";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL_LIST_ITEM._id";
    public static final String C_LIST_ID = "LIST_ID";
    public static final String FULL_C_LIST_ID = "INDIVIDUAL_LIST_ITEM.LIST_ID";
    public static final String C_INDIVIDUAL_ID = "INDIVIDUAL_ID";
    public static final String FULL_C_INDIVIDUAL_ID = "INDIVIDUAL_LIST_ITEM.INDIVIDUAL_ID";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST_ITEM (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "LIST_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_ID INTEGER NOT NULL," + 
        "FOREIGN KEY (LIST_ID) REFERENCES INDIVIDUAL_LIST (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST_ITEM;";
    public static final String INSERT_STATEMENT = "INSERT INTO INDIVIDUAL_LIST_ITEM (LIST_ID,INDIVIDUAL_ID) VALUES (?,?)";
    public static final String UPDATE_STATEMENT = "UPDATE INDIVIDUAL_LIST_ITEM SET LIST_ID=?, INDIVIDUAL_ID=? WHERE _id = ?";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_LIST_ID,
        C_INDIVIDUAL_ID};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_LIST_ID,
        FULL_C_INDIVIDUAL_ID};

    public IndividualListItemConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static long getListId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LIST_ID));
    }

    public static long getIndividualId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_ID));
    }


}
/*
 * IndividualListItemBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.other.individuallistitem;

import org.dbtools.android.domain.AndroidBaseRecord;
import android.database.Cursor;
import android.content.ContentValues;


@SuppressWarnings("all")
public abstract class IndividualListItemBaseRecord extends AndroidBaseRecord {

    public static final String DATABASE = "other";
    public static final String TABLE = "INDIVIDUAL_LIST_ITEM";
    public static final String FULL_TABLE = "other.INDIVIDUAL_LIST_ITEM";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL_LIST_ITEM._id";
    private long id = 0;
    public static final String C_LIST_ID = "LIST_ID";
    public static final String FULL_C_LIST_ID = "INDIVIDUAL_LIST_ITEM.LIST_ID";
    private long listId = 0;
    public static final String C_INDIVIDUAL_ID = "INDIVIDUAL_ID";
    public static final String FULL_C_INDIVIDUAL_ID = "INDIVIDUAL_LIST_ITEM.INDIVIDUAL_ID";
    private long individualId = 0;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST_ITEM (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "LIST_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_ID INTEGER NOT NULL," + 
        "FOREIGN KEY (LIST_ID) REFERENCES INDIVIDUAL_LIST (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST_ITEM;";
    public static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_LIST_ID,
        C_INDIVIDUAL_ID};

    public IndividualListItemBaseRecord() {
    }

    @Override
    public String getIdColumnName() {
        return C_ID;
    }

    @Override
    public long getPrimaryKeyId() {
        return id;
    }

    @Override
    public void setPrimaryKeyId(long id) {
        this.id = id;
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

    @Override
    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(C_LIST_ID, listId);
        values.put(C_INDIVIDUAL_ID, individualId);
        return values;
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            listId,
            individualId,
        };
        return values;
    }

    public void setContent(ContentValues values) {
        listId = values.getAsLong(C_LIST_ID);
        individualId = values.getAsLong(C_INDIVIDUAL_ID);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
        listId = cursor.getLong(cursor.getColumnIndexOrThrow(C_LIST_ID));
        individualId = cursor.getLong(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_ID));
    }

    public boolean isNewRecord() {
        return getPrimaryKeyId() <= 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(long individualId) {
        this.individualId = individualId;
    }


}
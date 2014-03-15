/*
 * IndividualListItemBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individuallistitem;

import org.company.project.domain.BaseRecord;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public class IndividualListItemBaseRecord extends BaseRecord {

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
    private Long individualId = null;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST_ITEM (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "LIST_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_ID INTEGER," + 
        "FOREIGN KEY (LIST_ID) REFERENCES INDIVIDUAL_LIST (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST_ITEM;";
     static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_LIST_ID,
        C_INDIVIDUAL_ID};

    public IndividualListItemBaseRecord() {
    }

    @Override
    public String getDatabaseName() {
        return DATABASE;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String getRowIDKey() {
        return C_ID;
    }

    @Override
    public long getPrimaryKeyID() {
        return id;
    }

    @Override
    public void setPrimaryKeyID(long id) {
        this.id = id;
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

    public void setContent(ContentValues values) {
        listId = values.getAsLong(C_LIST_ID);
        individualId = values.getAsLong(C_INDIVIDUAL_ID);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(C_ID));
        listId = cursor.getLong(cursor.getColumnIndex(C_LIST_ID));
        individualId = cursor.getLong(cursor.getColumnIndex(C_INDIVIDUAL_ID));
    }

    @Override
    public String toString() {
        String text = "\n";
        text += "id = "+ id +"\n";
        text += "listId = "+ listId +"\n";
        text += "individualId = "+ individualId +"\n";
        return text;
    }

    public boolean isNewRecord() {
        return getPrimaryKeyID() <= 0;
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

    public Long getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Long individualId) {
        this.individualId = individualId;
    }


}
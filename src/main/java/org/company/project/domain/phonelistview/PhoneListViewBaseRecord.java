/*
 * PhoneListViewBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.phonelistview;

import org.dbtools.android.domain.AndroidBaseRecord;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseRecord extends AndroidBaseRecord {

    public static final String DATABASE = "main";
    public static final String TABLE = "PHONE_LIST_VIEW";
    public static final String FULL_TABLE = "main.PHONE_LIST_VIEW";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "PHONE_LIST_VIEW._id";
    private Long id = null;
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "PHONE_LIST_VIEW.NAME";
    private String name = "";
     static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_NAME};

    public PhoneListViewBaseRecord() {
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
    public String getIdColumnName() {
        return null;
    }

    @Override
    public long getPrimaryKeyId() {
        return 0;
    }

    @Override
    public void setPrimaryKeyId(long id) {
    }

    @Override
    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(C_ID, id);
        values.put(C_NAME, name);
        return values;
    }

    public void setContent(ContentValues values) {
        id = values.getAsLong(C_ID);
        name = values.getAsString(C_NAME);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(C_ID));
        name = cursor.getString(cursor.getColumnIndex(C_NAME));
    }

    @Override
    public String toString() {
        String text = "\n";
        text += "id = "+ id +"\n";
        text += "name = "+ name +"\n";
        return text;
    }

    public boolean isNewRecord() {
        return getPrimaryKeyId() <= 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
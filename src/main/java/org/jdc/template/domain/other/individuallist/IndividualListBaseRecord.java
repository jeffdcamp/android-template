/*
 * IndividualListBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.other.individuallist;

import org.dbtools.android.domain.AndroidBaseRecord;
import android.database.Cursor;
import android.content.ContentValues;


@SuppressWarnings("all")
public abstract class IndividualListBaseRecord extends AndroidBaseRecord {

    public static final String DATABASE = "other";
    public static final String TABLE = "INDIVIDUAL_LIST";
    public static final String FULL_TABLE = "other.INDIVIDUAL_LIST";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL_LIST._id";
    private long id = 0;
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "INDIVIDUAL_LIST.NAME";
    private String name = "";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_LIST (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_LIST;";
    public static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_NAME};

    public IndividualListBaseRecord() {
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

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
    }

    @Override
    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(C_NAME, name);
        return values;
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            name,
        };
        return values;
    }

    public void setContent(ContentValues values) {
        name = values.getAsString(C_NAME);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
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

    @javax.annotation.Nonnull
    public String getName() {
        return name;
    }

    public void setName(@javax.annotation.Nonnull String name) {
        this.name = name;
    }


}
/*
 * IndividualListBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individuallist;

import org.company.project.domain.BaseRecord;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public class IndividualListBaseRecord extends BaseRecord {

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
     static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_NAME};

    public IndividualListBaseRecord() {
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
        values.put(C_NAME, name);
        return values;
    }

    public void setContent(ContentValues values) {
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
        return getPrimaryKeyID() <= 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
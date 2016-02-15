/*
 * IndividualQueryBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individualquery;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.DBToolsDateFormatter;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class IndividualQueryBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private String name = "";

    public IndividualQueryBaseRecord() {
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
    public String[] getAllColumns() {
        return IndividualQueryConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return IndividualQueryConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(IndividualQueryConst.C_ID, id);
        values.put(IndividualQueryConst.C_NAME, name);
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
        id = values.getAsLong(IndividualQueryConst.C_ID);
        name = values.getAsString(IndividualQueryConst.C_NAME);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualQueryConst.C_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(IndividualQueryConst.C_NAME));
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
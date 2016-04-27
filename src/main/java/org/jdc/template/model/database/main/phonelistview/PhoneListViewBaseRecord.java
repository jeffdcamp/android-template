/*
 * PhoneListViewBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.phonelistview;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.database.statement.StatementWrapper;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private String name = "";

    public PhoneListViewBaseRecord() {
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
        return PhoneListViewConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return PhoneListViewConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public void getContentValues(DBToolsContentValues values) {
        values.put(PhoneListViewConst.C_ID, id);
        values.put(PhoneListViewConst.C_NAME, name);
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            name,
        };
        return values;
    }

    @Override
    public String[] getBindInsertValues() {
        String[] values = new String[]{
            String.valueOf(id),
            name != null ? String.valueOf(name) : null,
        };
        return values;
    }

    @Override
    public void bindInsertStatement(StatementWrapper statement) {
        statement.bindLong( 1, id);
        if (name != null) {
            statement.bindString(2, name);
        } else {
            statement.bindNull(2);
        }
    }

    @Override
    public void bindUpdateStatement(StatementWrapper statement) {
        statement.bindLong( 1, id);
        if (name != null) {
            statement.bindString(2, name);
        } else {
            statement.bindNull(2);
        }
    }

    public void setContent(DBToolsContentValues values) {
        id = values.getAsLong(PhoneListViewConst.C_ID);
        name = values.getAsString(PhoneListViewConst.C_NAME);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(PhoneListViewConst.C_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(PhoneListViewConst.C_NAME));
    }

    public boolean isNewRecord() {
        return getPrimaryKeyId() <= 0;
    }

    public long getId() {
        return id;
    }

    @javax.annotation.Nonnull
    public String getName() {
        return name;
    }


}
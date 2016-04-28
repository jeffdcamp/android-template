/*
 * CrossDatabaseQueryBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.attached.crossdatabasequery;

import android.database.Cursor;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import org.dbtools.android.domain.database.statement.StatementWrapper;


@SuppressWarnings("all")
public abstract class CrossDatabaseQueryBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private String name = "";
    private LocationType type = LocationType.HOME;

    public CrossDatabaseQueryBaseRecord() {
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
        return CrossDatabaseQueryConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return CrossDatabaseQueryConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public void getContentValues(DBToolsContentValues values) {
        values.put(CrossDatabaseQueryConst.C_ID, id);
        values.put(CrossDatabaseQueryConst.C_NAME, name);
        values.put(CrossDatabaseQueryConst.C_TYPE, type.ordinal());
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            name,
            type.ordinal(),
        };
        return values;
    }

    @Override
    public void bindInsertStatement(StatementWrapper statement) {
        statement.bindLong(1, id);
        statement.bindString(2, name);
        statement.bindLong(3, type.ordinal());
    }

    @Override
    public void bindUpdateStatement(StatementWrapper statement) {
        statement.bindLong(1, id);
        statement.bindString(2, name);
        statement.bindLong(3, type.ordinal());
    }

    public void setContent(DBToolsContentValues values) {
        id = values.getAsLong(CrossDatabaseQueryConst.C_ID);
        name = values.getAsString(CrossDatabaseQueryConst.C_NAME);
        type = LocationType.values()[values.getAsInteger(CrossDatabaseQueryConst.C_TYPE)];
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_NAME));
        type = LocationType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_TYPE))];
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

    @javax.annotation.Nonnull
    public LocationType getType() {
        return type;
    }


}
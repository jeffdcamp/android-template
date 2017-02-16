/*
 * HouseholdBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.household;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.database.statement.StatementWrapper;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class HouseholdBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private String name = "";

    public HouseholdBaseRecord() {
    }

    @Override
    public String getIdColumnName() {
        return HouseholdConst.C_ID;
    }

    @Override
    public long getPrimaryKeyId() {
        return id;
    }

    @Override
    public void setPrimaryKeyId(long id) {
        this.id = id;
    }

    @Override
    public String[] getAllColumns() {
        return HouseholdConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return HouseholdConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public void getContentValues(DBToolsContentValues values) {
        values.put(HouseholdConst.C_NAME, name);
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            name,
        };
        return values;
    }

    public Household copy() {
        Household copy = new Household();
        copy.setId(id);
        copy.setName(name);
        return copy;
    }

    @Override
    public void bindInsertStatement(StatementWrapper statement) {
        statement.bindString(1, name);
    }

    @Override
    public void bindUpdateStatement(StatementWrapper statement) {
        statement.bindString(1, name);
        statement.bindLong(2, id);
    }

    public void setContent(DBToolsContentValues values) {
        name = values.getAsString(HouseholdConst.C_NAME);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(HouseholdConst.C_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(HouseholdConst.C_NAME));
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
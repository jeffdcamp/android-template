/*
 * IndividualDataBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individualdata;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.database.statement.StatementWrapper;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class IndividualDataBaseRecord extends AndroidBaseRecord {

    private long externalId = 0;
    private int typeId = 0;
    private String name = "";

    public IndividualDataBaseRecord() {
    }

    @Override
    public String[] getAllColumns() {
        return IndividualDataConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return IndividualDataConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public void getContentValues(DBToolsContentValues values) {
        values.put(IndividualDataConst.C_EXTERNAL_ID, externalId);
        values.put(IndividualDataConst.C_TYPE_ID, typeId);
        values.put(IndividualDataConst.C_NAME, name);
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            externalId,
            typeId,
            name,
        };
        return values;
    }

    public IndividualData copy() {
        IndividualData copy = new IndividualData();
        copy.setExternalId(externalId);
        copy.setTypeId(typeId);
        copy.setName(name);
        return copy;
    }

    @Override
    public void bindInsertStatement(StatementWrapper statement) {
        statement.bindLong(1, externalId);
        statement.bindLong(2, typeId);
        statement.bindString(3, name);
    }

    @Override
    public void bindUpdateStatement(StatementWrapper statement) {
        statement.bindLong(1, externalId);
        statement.bindLong(2, typeId);
        statement.bindString(3, name);
    }

    public void setContent(DBToolsContentValues values) {
        externalId = values.getAsLong(IndividualDataConst.C_EXTERNAL_ID);
        typeId = values.getAsInteger(IndividualDataConst.C_TYPE_ID);
        name = values.getAsString(IndividualDataConst.C_NAME);
    }

    @Override
    public void setContent(Cursor cursor) {
        externalId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualDataConst.C_EXTERNAL_ID));
        typeId = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualDataConst.C_TYPE_ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(IndividualDataConst.C_NAME));
    }

    public boolean isNewRecord() {
        return getPrimaryKeyId() <= 0;
    }

    @Override
    public String getIdColumnName() {
        return "NO_PRIMARY_KEY";
    }

    @Override
    public long getPrimaryKeyId() {
        return 0;
    }

    @Override
    public void setPrimaryKeyId(long id) {
        // NO_PRIMARY_KEY
    }

    public long getExternalId() {
        return externalId;
    }

    public void setExternalId(long externalId) {
        this.externalId = externalId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @javax.annotation.Nonnull
    public String getName() {
        return name;
    }

    public void setName(@javax.annotation.Nonnull String name) {
        this.name = name;
    }


}
/*
 * IndividualListItemBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallistitem;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.database.statement.StatementWrapper;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class IndividualListItemBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private long listId = 0;
    private long individualId = 0;

    public IndividualListItemBaseRecord() {
    }

    @Override
    public String getIdColumnName() {
        return IndividualListItemConst.C_ID;
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
        return IndividualListItemConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return IndividualListItemConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public void getContentValues(DBToolsContentValues values) {
        values.put(IndividualListItemConst.C_LIST_ID, listId);
        values.put(IndividualListItemConst.C_INDIVIDUAL_ID, individualId);
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

    public IndividualListItem copy() {
        IndividualListItem copy = new IndividualListItem();
        copy.setId(id);
        copy.setListId(listId);
        copy.setIndividualId(individualId);
        return copy;
    }

    @Override
    public void bindInsertStatement(StatementWrapper statement) {
        statement.bindLong(1, listId);
        statement.bindLong(2, individualId);
    }

    @Override
    public void bindUpdateStatement(StatementWrapper statement) {
        statement.bindLong(1, listId);
        statement.bindLong(2, individualId);
        statement.bindLong(3, id);
    }

    public void setContent(DBToolsContentValues values) {
        listId = values.getAsLong(IndividualListItemConst.C_LIST_ID);
        individualId = values.getAsLong(IndividualListItemConst.C_INDIVIDUAL_ID);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_ID));
        listId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_LIST_ID));
        individualId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListItemConst.C_INDIVIDUAL_ID));
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
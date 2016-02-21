/*
 * IndividualListBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.other.individuallist;

import org.jdc.template.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.AndroidBaseManagerWritable;


@SuppressWarnings("all")
public abstract class IndividualListBaseManager extends AndroidBaseManagerWritable<IndividualList> {

    private DatabaseManager databaseManager;

    public IndividualListBaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualListConst.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualList newRecord() {
        return new IndividualList();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return IndividualListConst.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllColumns() {
        return IndividualListConst.ALL_COLUMNS;
    }

    @javax.annotation.Nonnull
    public DatabaseWrapper getReadableDatabase(@javax.annotation.Nonnull String databaseName) {
        return databaseManager.getReadableDatabase(databaseName);
    }

    @javax.annotation.Nonnull
    public DatabaseWrapper getReadableDatabase() {
        return databaseManager.getReadableDatabase(getDatabaseName());
    }

    @javax.annotation.Nonnull
    public DatabaseWrapper getWritableDatabase(@javax.annotation.Nonnull String databaseName) {
        return databaseManager.getWritableDatabase(databaseName);
    }

    @javax.annotation.Nonnull
    public DatabaseWrapper getWritableDatabase() {
        return databaseManager.getWritableDatabase(getDatabaseName());
    }

    @javax.annotation.Nonnull
    public org.dbtools.android.domain.AndroidDatabase getAndroidDatabase(@javax.annotation.Nonnull String databaseName) {
        return databaseManager.getDatabase(databaseName);
    }

    @javax.annotation.Nonnull
    public String getPrimaryKey() {
        return IndividualListConst.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return IndividualListConst.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return IndividualListConst.CREATE_TABLE;
    }


}
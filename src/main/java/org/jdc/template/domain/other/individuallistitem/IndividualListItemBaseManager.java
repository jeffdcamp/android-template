/*
 * IndividualListItemBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.other.individuallistitem;

import org.jdc.template.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.RxAndroidBaseManagerWritable;


@SuppressWarnings("all")
public abstract class IndividualListItemBaseManager extends RxAndroidBaseManagerWritable<IndividualListItem> {

    private DatabaseManager databaseManager;

    public IndividualListItemBaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualListItemConst.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualListItem newRecord() {
        return new IndividualListItem();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return IndividualListItemConst.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllColumns() {
        return IndividualListItemConst.ALL_COLUMNS;
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
        return IndividualListItemConst.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return IndividualListItemConst.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return IndividualListItemConst.CREATE_TABLE;
    }


}
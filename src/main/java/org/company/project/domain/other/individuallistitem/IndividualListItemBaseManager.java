/*
 * IndividualListItemBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.other.individuallistitem;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class IndividualListItemBaseManager extends AndroidBaseManager<IndividualListItem> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     com.squareup.otto.Bus bus;

    public IndividualListItemBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualListItem.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualListItem newRecord() {
        return new IndividualListItem();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return IndividualListItem.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return IndividualListItem.ALL_KEYS;
    }

    @javax.annotation.Nonnull
    public SQLiteDatabase getReadableDatabase(@javax.annotation.Nonnull String databaseName) {
        return databaseManager.getReadableDatabase(databaseName);
    }

    @javax.annotation.Nonnull
    public SQLiteDatabase getReadableDatabase() {
        return databaseManager.getReadableDatabase(getDatabaseName());
    }

    @javax.annotation.Nonnull
    public SQLiteDatabase getWritableDatabase(@javax.annotation.Nonnull String databaseName) {
        return databaseManager.getWritableDatabase(databaseName);
    }

    @javax.annotation.Nonnull
    public SQLiteDatabase getWritableDatabase() {
        return databaseManager.getWritableDatabase(getDatabaseName());
    }

    @javax.annotation.Nonnull
    public String getPrimaryKey() {
        return IndividualListItem.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return IndividualListItem.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return IndividualListItem.CREATE_TABLE;
    }

    public com.squareup.otto.Bus getBus() {
        return bus;
    }

    public void setBus(com.squareup.otto.Bus bus) {
        this.bus = bus;
    }


}
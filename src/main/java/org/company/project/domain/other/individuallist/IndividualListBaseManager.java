/*
 * IndividualListBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.other.individuallist;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class IndividualListBaseManager extends AndroidBaseManager<IndividualList> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     com.squareup.otto.Bus bus;

    public IndividualListBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualList.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualList newRecord() {
        return new IndividualList();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return IndividualList.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return IndividualList.ALL_KEYS;
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
        return IndividualList.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return IndividualList.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return IndividualList.CREATE_TABLE;
    }

    public com.squareup.otto.Bus getBus() {
        return bus;
    }

    public void setBus(com.squareup.otto.Bus bus) {
        this.bus = bus;
    }


}
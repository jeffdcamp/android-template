/*
 * PhoneListViewBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.main.phonelistview;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseManager extends AndroidBaseManager<PhoneListView> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     com.squareup.otto.Bus bus;

    public PhoneListViewBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return PhoneListView.DATABASE;
    }

    @javax.annotation.Nonnull
    public PhoneListView newRecord() {
        return new PhoneListView();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return PhoneListView.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return PhoneListView.ALL_KEYS;
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
        return null;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return PhoneListView.DROP_VIEW;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return PhoneListView.CREATE_VIEW;
    }

    @Override
    public boolean save(@javax.annotation.Nonnull String databaseName, @javax.annotation.Nonnull PhoneListView e) {
        throw new IllegalStateException("Cannot call SAVE on a PhoneListView View or Query");
    }

    public com.squareup.otto.Bus getBus() {
        return bus;
    }

    public void setBus(com.squareup.otto.Bus bus) {
        this.bus = bus;
    }


}
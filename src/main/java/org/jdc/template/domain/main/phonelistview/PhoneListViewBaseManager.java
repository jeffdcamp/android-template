/*
 * PhoneListViewBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.main.phonelistview;

import org.jdc.template.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.AndroidBaseManagerReadOnly;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseManager extends AndroidBaseManagerReadOnly<PhoneListView> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     org.dbtools.android.domain.DBToolsEventBus bus;

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

    public org.dbtools.android.domain.DBToolsEventBus getBus() {
        return bus;
    }

    public void setBus(org.dbtools.android.domain.DBToolsEventBus bus) {
        this.bus = bus;
    }


}
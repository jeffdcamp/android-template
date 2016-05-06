/*
 * PhoneListViewBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.phonelistview;

import org.jdc.template.model.database.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.RxAndroidBaseManagerReadOnly;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseManager extends RxAndroidBaseManagerReadOnly<PhoneListView> {

    private DatabaseManager databaseManager;

    public PhoneListViewBaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return PhoneListViewConst.DATABASE;
    }

    @javax.annotation.Nonnull
    public PhoneListView newRecord() {
        return new PhoneListView();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return PhoneListViewConst.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllColumns() {
        return PhoneListViewConst.ALL_COLUMNS;
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

    public org.dbtools.android.domain.config.DatabaseConfig getDatabaseConfig() {
        return databaseManager.getDatabaseConfig();
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

    @javax.annotation.Nonnull
    public String getInsertSql() {
        return "";
    }

    @javax.annotation.Nonnull
    public String getUpdateSql() {
        return "";
    }


}
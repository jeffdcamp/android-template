/*
 * PhoneListViewBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.phonelistview;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseManager extends AndroidBaseManager<PhoneListView> {

    @javax.inject.Inject      DatabaseManager databaseManager;

    public PhoneListViewBaseManager() {
    }

    public String getDatabaseName() {
        return PhoneListView.DATABASE;
    }

    public PhoneListView newRecord() {
        return new PhoneListView();
    }

    public String getTableName() {
        return PhoneListView.TABLE;
    }

    public String[] getAllKeys() {
        return PhoneListView.ALL_KEYS;
    }

    public SQLiteDatabase getReadableDatabase(String databaseName) {
        return databaseManager.getReadableDatabase(databaseName);
    }

    public SQLiteDatabase getReadableDatabase() {
        return databaseManager.getReadableDatabase(getDatabaseName());
    }

    public SQLiteDatabase getWritableDatabase(String databaseName) {
        return databaseManager.getWritableDatabase(databaseName);
    }

    public SQLiteDatabase getWritableDatabase() {
        return databaseManager.getWritableDatabase(getDatabaseName());
    }

    public String getPrimaryKey() {
        return null;
    }

    public String getDropSql() {
        return PhoneListView.DROP_VIEW;
    }

    public String getCreateSql() {
        return PhoneListView.CREATE_VIEW;
    }

    @Override
    public boolean save(String databaseName, PhoneListView e) {
        throw new IllegalStateException("Cannot call SAVE on PhoneListView View");
    }


}
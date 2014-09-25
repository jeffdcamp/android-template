/*
 * CrossDatabaseQueryBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.attached.crossdatabasequery;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class CrossDatabaseQueryBaseManager extends AndroidBaseManager<CrossDatabaseQuery> {

    @javax.inject.Inject
     DatabaseManager databaseManager;

    public CrossDatabaseQueryBaseManager() {
    }

    public String getDatabaseName() {
        return CrossDatabaseQuery.DATABASE;
    }

    public CrossDatabaseQuery newRecord() {
        return new CrossDatabaseQuery();
    }

    public String[] getAllKeys() {
        return CrossDatabaseQuery.ALL_KEYS;
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

    public abstract String getQuery();

    public String getTableName() {
        return getQuery();
    }

    public String getPrimaryKey() {
        return null;
    }

    public String getDropSql() {
        return "";
    }

    public String getCreateSql() {
        return "";
    }

    @Override
    public boolean save(String databaseName, CrossDatabaseQuery e) {
        throw new IllegalStateException("Cannot call SAVE on a CrossDatabaseQuery View or Query");
    }


}
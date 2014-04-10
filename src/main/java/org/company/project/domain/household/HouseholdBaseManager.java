/*
 * HouseholdBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.household;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class HouseholdBaseManager extends AndroidBaseManager<Household> {

    @javax.inject.Inject
     DatabaseManager databaseManager;

    public HouseholdBaseManager() {
    }

    public String getDatabaseName() {
        return Household.DATABASE;
    }

    public Household newRecord() {
        return new Household();
    }

    public String getTableName() {
        return Household.TABLE;
    }

    public String[] getAllKeys() {
        return Household.ALL_KEYS;
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
        return Household.PRIMARY_KEY_COLUMN;
    }

    public String getDropSql() {
        return Household.DROP_TABLE;
    }

    public String getCreateSql() {
        return Household.CREATE_TABLE;
    }


}
/*
 * IndividualBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.individual;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class IndividualBaseManager extends AndroidBaseManager<Individual> {

    @javax.inject.Inject
     DatabaseManager databaseManager;

    public IndividualBaseManager() {
    }

    public String getDatabaseName() {
        return Individual.DATABASE;
    }

    public Individual newRecord() {
        return new Individual();
    }

    public String getTableName() {
        return Individual.TABLE;
    }

    public String[] getAllKeys() {
        return Individual.ALL_KEYS;
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
        return Individual.PRIMARY_KEY_COLUMN;
    }

    public String getDropSql() {
        return Individual.DROP_TABLE;
    }

    public String getCreateSql() {
        return Individual.CREATE_TABLE;
    }


}
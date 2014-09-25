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

    public IndividualListBaseManager() {
    }

    public String getDatabaseName() {
        return IndividualList.DATABASE;
    }

    public IndividualList newRecord() {
        return new IndividualList();
    }

    public String getTableName() {
        return IndividualList.TABLE;
    }

    public String[] getAllKeys() {
        return IndividualList.ALL_KEYS;
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
        return IndividualList.PRIMARY_KEY_COLUMN;
    }

    public String getDropSql() {
        return IndividualList.DROP_TABLE;
    }

    public String getCreateSql() {
        return IndividualList.CREATE_TABLE;
    }


}
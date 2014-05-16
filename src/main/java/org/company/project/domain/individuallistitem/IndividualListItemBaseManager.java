/*
 * IndividualListItemBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.individuallistitem;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class IndividualListItemBaseManager extends AndroidBaseManager<IndividualListItem> {

    @javax.inject.Inject      DatabaseManager databaseManager;

    public IndividualListItemBaseManager() {
    }

    public String getDatabaseName() {
        return IndividualListItem.DATABASE;
    }

    public IndividualListItem newRecord() {
        return new IndividualListItem();
    }

    public String getTableName() {
        return IndividualListItem.TABLE;
    }

    public String[] getAllKeys() {
        return IndividualListItem.ALL_KEYS;
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
        return IndividualListItem.PRIMARY_KEY_COLUMN;
    }

    public String getDropSql() {
        return IndividualListItem.DROP_TABLE;
    }

    public String getCreateSql() {
        return IndividualListItem.CREATE_TABLE;
    }


}
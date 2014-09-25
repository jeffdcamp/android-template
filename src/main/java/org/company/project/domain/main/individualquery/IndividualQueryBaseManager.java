/*
 * IndividualQueryBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.main.individualquery;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("all")
public abstract class IndividualQueryBaseManager extends AndroidBaseManager<IndividualQuery> {

    @javax.inject.Inject
     DatabaseManager databaseManager;

    public IndividualQueryBaseManager() {
    }

    public String getDatabaseName() {
        return IndividualQuery.DATABASE;
    }

    public IndividualQuery newRecord() {
        return new IndividualQuery();
    }

    public String[] getAllKeys() {
        return IndividualQuery.ALL_KEYS;
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
    public boolean save(String databaseName, IndividualQuery e) {
        throw new IllegalStateException("Cannot call SAVE on a IndividualQuery View or Query");
    }


}
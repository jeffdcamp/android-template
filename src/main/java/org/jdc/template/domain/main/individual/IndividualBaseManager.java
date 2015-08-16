/*
 * IndividualBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.main.individual;

import org.jdc.template.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.AndroidBaseManagerWritable;


@SuppressWarnings("all")
public abstract class IndividualBaseManager extends AndroidBaseManagerWritable<Individual> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     org.dbtools.android.domain.DBToolsEventBus bus;

    public IndividualBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return Individual.DATABASE;
    }

    @javax.annotation.Nonnull
    public Individual newRecord() {
        return new Individual();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return Individual.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return Individual.ALL_KEYS;
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
        return Individual.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return Individual.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return Individual.CREATE_TABLE;
    }

    public org.dbtools.android.domain.DBToolsEventBus getBus() {
        return bus;
    }

    public void setBus(org.dbtools.android.domain.DBToolsEventBus bus) {
        this.bus = bus;
    }


}
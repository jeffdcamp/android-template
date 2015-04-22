/*
 * IndividualBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.main.individual;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;


@SuppressWarnings("all")
public abstract class IndividualBaseManager extends AndroidBaseManager<Individual> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     com.squareup.otto.Bus bus;

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

    public com.squareup.otto.Bus getBus() {
        return bus;
    }

    public void setBus(com.squareup.otto.Bus bus) {
        this.bus = bus;
    }


}
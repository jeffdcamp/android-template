/*
 * HouseholdBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.company.project.domain.main.household;

import org.dbtools.android.domain.AndroidBaseManager;
import org.company.project.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;


@SuppressWarnings("all")
public abstract class HouseholdBaseManager extends AndroidBaseManager<Household> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     org.dbtools.android.domain.DBToolsEventBus bus;

    public HouseholdBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return Household.DATABASE;
    }

    @javax.annotation.Nonnull
    public Household newRecord() {
        return new Household();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return Household.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return Household.ALL_KEYS;
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
        return Household.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return Household.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return Household.CREATE_TABLE;
    }

    public org.dbtools.android.domain.DBToolsEventBus getBus() {
        return bus;
    }

    public void setBus(org.dbtools.android.domain.DBToolsEventBus bus) {
        this.bus = bus;
    }


}
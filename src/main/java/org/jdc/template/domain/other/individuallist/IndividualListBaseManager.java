/*
 * IndividualListBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.other.individuallist;

import org.jdc.template.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.AndroidBaseManagerWritable;


@SuppressWarnings("all")
public abstract class IndividualListBaseManager extends AndroidBaseManagerWritable<IndividualList> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     org.dbtools.android.domain.DBToolsEventBus bus;

    public IndividualListBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualList.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualList newRecord() {
        return new IndividualList();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return IndividualList.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return IndividualList.ALL_KEYS;
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
        return IndividualList.PRIMARY_KEY_COLUMN;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return IndividualList.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return IndividualList.CREATE_TABLE;
    }

    public org.dbtools.android.domain.DBToolsEventBus getBus() {
        return bus;
    }

    public void setBus(org.dbtools.android.domain.DBToolsEventBus bus) {
        this.bus = bus;
    }


}
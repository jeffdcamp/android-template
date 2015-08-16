/*
 * CrossDatabaseQueryBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.attached.crossdatabasequery;

import org.jdc.template.domain.DatabaseManager;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.AndroidBaseManagerReadOnly;


@SuppressWarnings("all")
public abstract class CrossDatabaseQueryBaseManager extends AndroidBaseManagerReadOnly<CrossDatabaseQuery> {

    @javax.inject.Inject
     DatabaseManager databaseManager;
    @javax.inject.Inject
     org.dbtools.android.domain.DBToolsEventBus bus;

    public CrossDatabaseQueryBaseManager() {
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return CrossDatabaseQuery.DATABASE;
    }

    @javax.annotation.Nonnull
    public CrossDatabaseQuery newRecord() {
        return new CrossDatabaseQuery();
    }

    @javax.annotation.Nonnull
    public String[] getAllKeys() {
        return CrossDatabaseQuery.ALL_KEYS;
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

    public abstract String getQuery();

    @javax.annotation.Nonnull
    public String getTableName() {
        return getQuery();
    }

    @javax.annotation.Nonnull
    public String getPrimaryKey() {
        return null;
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return "";
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return "";
    }

    public org.dbtools.android.domain.DBToolsEventBus getBus() {
        return bus;
    }

    public void setBus(org.dbtools.android.domain.DBToolsEventBus bus) {
        this.bus = bus;
    }


}
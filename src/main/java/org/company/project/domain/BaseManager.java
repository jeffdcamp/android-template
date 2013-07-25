package org.company.project.domain;

import android.database.sqlite.SQLiteDatabase;
import org.dbtools.android.domain.AndroidBaseManager;

import javax.inject.Inject;

public abstract class BaseManager<T extends BaseRecord> extends AndroidBaseManager<T> {

    @Inject
    public DatabaseManager databaseManager;

    public SQLiteDatabase getReadableDatabase() {
        return databaseManager.getReadableDatabase(getDatabaseName());
    }

    public SQLiteDatabase getReadableDatabase(String databaseName) {
        return databaseManager.getReadableDatabase(databaseName);
    }

    public SQLiteDatabase getWritableDatabase() {
        return databaseManager.getWritableDatabase(getDatabaseName());
    }

    public SQLiteDatabase getWritableDatabase(String databaseName) {
        return databaseManager.getWritableDatabase(databaseName);
    }

}

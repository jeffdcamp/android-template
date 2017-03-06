/*
 * IndividualDataBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individualdata;

import org.jdc.template.model.database.DatabaseManager;
import org.dbtools.android.domain.RxAndroidBaseManagerWritable;


@SuppressWarnings("all")
public abstract class IndividualDataBaseManager extends RxAndroidBaseManagerWritable<IndividualData> {


    public IndividualDataBaseManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualDataConst.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualData newRecord() {
        return new IndividualData();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return IndividualDataConst.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllColumns() {
        return IndividualDataConst.ALL_COLUMNS;
    }

    @javax.annotation.Nonnull
    public String getPrimaryKey() {
        return "NO_PRIMARY_KEY";
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return IndividualDataConst.DROP_TABLE;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return IndividualDataConst.CREATE_TABLE;
    }

    @javax.annotation.Nonnull
    public String getInsertSql() {
        return IndividualDataConst.INSERT_STATEMENT;
    }

    @javax.annotation.Nonnull
    public String getUpdateSql() {
        return IndividualDataConst.UPDATE_STATEMENT;
    }


}
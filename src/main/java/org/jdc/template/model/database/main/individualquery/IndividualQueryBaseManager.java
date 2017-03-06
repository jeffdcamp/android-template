/*
 * IndividualQueryBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individualquery;

import org.jdc.template.model.database.DatabaseManager;
import org.dbtools.android.domain.RxAndroidBaseManagerReadOnly;


@SuppressWarnings("all")
public abstract class IndividualQueryBaseManager extends RxAndroidBaseManagerReadOnly<IndividualQuery> {


    public IndividualQueryBaseManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return IndividualQueryConst.DATABASE;
    }

    @javax.annotation.Nonnull
    public IndividualQuery newRecord() {
        return new IndividualQuery();
    }

    @javax.annotation.Nonnull
    public String[] getAllColumns() {
        return IndividualQueryConst.ALL_COLUMNS;
    }

    public abstract String getQuery();

    @javax.annotation.Nonnull
    public String getTableName() {
        return getQuery();
    }

    @javax.annotation.Nonnull
    public String getPrimaryKey() {
        return "<NO_PRIMARY_KEY_ON_QUERIES>";
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return "";
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return "";
    }

    @javax.annotation.Nonnull
    public String getInsertSql() {
        return "";
    }

    @javax.annotation.Nonnull
    public String getUpdateSql() {
        return "";
    }


}
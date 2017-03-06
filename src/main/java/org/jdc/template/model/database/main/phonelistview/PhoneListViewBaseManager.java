/*
 * PhoneListViewBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.phonelistview;

import org.jdc.template.model.database.DatabaseManager;
import org.dbtools.android.domain.RxAndroidBaseManagerReadOnly;


@SuppressWarnings("all")
public abstract class PhoneListViewBaseManager extends RxAndroidBaseManagerReadOnly<PhoneListView> {


    public PhoneListViewBaseManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @javax.annotation.Nonnull
    public String getDatabaseName() {
        return PhoneListViewConst.DATABASE;
    }

    @javax.annotation.Nonnull
    public PhoneListView newRecord() {
        return new PhoneListView();
    }

    @javax.annotation.Nonnull
    public String getTableName() {
        return PhoneListViewConst.TABLE;
    }

    @javax.annotation.Nonnull
    public String[] getAllColumns() {
        return PhoneListViewConst.ALL_COLUMNS;
    }

    @javax.annotation.Nonnull
    public String getPrimaryKey() {
        return "<NO_PRIMARY_KEY_ON_VIEWS>";
    }

    @javax.annotation.Nonnull
    public String getDropSql() {
        return PhoneListViewManager.DROP_VIEW;
    }

    @javax.annotation.Nonnull
    public String getCreateSql() {
        return PhoneListViewManager.CREATE_VIEW;
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
/*
 * HouseholdBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.household;

import org.company.project.domain.BaseManager;


@SuppressWarnings("all")
public class HouseholdBaseManager extends BaseManager<Household> {


    public HouseholdBaseManager() {
    }

    public String getDatabaseName() {
        return Household.DATABASE;
    }

    public String getTableName() {
        return Household.TABLE;
    }

    public String getPrimaryKey() {
        return Household.PRIMARY_KEY_COLUMN;
    }

    public String[] getAllKeys() {
        return Household.ALL_KEYS;
    }

    public String getDropTableSQL() {
        return Household.DROP_TABLE;
    }

    public String getCreateTableSQL() {
        return Household.CREATE_TABLE;
    }

    public Household newRecord() {
        return new Household();
    }


}
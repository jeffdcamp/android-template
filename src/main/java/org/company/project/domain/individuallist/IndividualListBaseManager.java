/*
 * IndividualListBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individuallist;

import org.company.project.domain.BaseManager;


@SuppressWarnings("all")
public class IndividualListBaseManager extends BaseManager<IndividualList> {


    public IndividualListBaseManager() {
    }

    public String getDatabaseName() {
        return IndividualList.DATABASE;
    }

    public String getTableName() {
        return IndividualList.TABLE;
    }

    public String getPrimaryKey() {
        return IndividualList.PRIMARY_KEY_COLUMN;
    }

    public String[] getAllKeys() {
        return IndividualList.ALL_KEYS;
    }

    public String getDropTableSQL() {
        return IndividualList.DROP_TABLE;
    }

    public String getCreateTableSQL() {
        return IndividualList.CREATE_TABLE;
    }

    public IndividualList newRecord() {
        return new IndividualList();
    }


}
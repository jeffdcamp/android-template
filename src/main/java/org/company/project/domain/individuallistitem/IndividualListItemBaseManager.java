/*
 * IndividualListItemBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individuallistitem;

import org.company.project.domain.BaseManager;


@SuppressWarnings("all")
public class IndividualListItemBaseManager extends BaseManager<IndividualListItem> {


    public IndividualListItemBaseManager() {
    }

    public String getDatabaseName() {
        return IndividualListItem.DATABASE;
    }

    public String getTableName() {
        return IndividualListItem.TABLE;
    }

    public String getPrimaryKey() {
        return IndividualListItem.PRIMARY_KEY_COLUMN;
    }

    public String[] getAllKeys() {
        return IndividualListItem.ALL_KEYS;
    }

    public String getDropTableSQL() {
        return IndividualListItem.DROP_TABLE;
    }

    public String getCreateTableSQL() {
        return IndividualListItem.CREATE_TABLE;
    }

    public IndividualListItem newRecord() {
        return new IndividualListItem();
    }


}
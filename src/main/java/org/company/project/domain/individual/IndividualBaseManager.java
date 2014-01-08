/*
 * IndividualBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individual;

import org.company.project.domain.BaseManager;


@SuppressWarnings("all")
public class IndividualBaseManager extends BaseManager<Individual> {


    public IndividualBaseManager() {
    }

    public String getDatabaseName() {
        return Individual.DATABASE;
    }

    public String getTableName() {
        return Individual.TABLE;
    }

    public String getPrimaryKey() {
        return Individual.PRIMARY_KEY_COLUMN;
    }

    public String[] getAllKeys() {
        return Individual.ALL_KEYS;
    }

    public String getDropTableSQL() {
        return Individual.DROP_TABLE;
    }

    public String getCreateTableSQL() {
        return Individual.CREATE_TABLE;
    }

    public Individual newRecord() {
        return new Individual();
    }


}
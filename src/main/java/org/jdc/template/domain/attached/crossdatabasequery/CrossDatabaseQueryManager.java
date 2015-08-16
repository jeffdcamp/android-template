/*
 * CrossDatabaseQueryManager.java
 *
 * Generated on: 09/18/2014 09:40:57
 *
 */



package org.jdc.template.domain.attached.crossdatabasequery;


import javax.inject.Inject;

@javax.inject.Singleton
public class CrossDatabaseQueryManager extends CrossDatabaseQueryBaseManager {

    @Inject
    public CrossDatabaseQueryManager() {
    }

    @Override
    public String getQuery() {
        return CrossDatabaseQuery.QUERY;
    }


}
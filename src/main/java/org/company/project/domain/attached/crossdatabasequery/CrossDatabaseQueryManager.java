/*
 * CrossDatabaseQueryManager.java
 *
 * Generated on: 09/18/2014 09:40:57
 *
 */



package org.company.project.domain.attached.crossdatabasequery;


@javax.inject.Singleton
public class CrossDatabaseQueryManager extends CrossDatabaseQueryBaseManager {


    public CrossDatabaseQueryManager() {
    }

    @Override
    public String getQuery() {
        return CrossDatabaseQuery.QUERY;
    }


}
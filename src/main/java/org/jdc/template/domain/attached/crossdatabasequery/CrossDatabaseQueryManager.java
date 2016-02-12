/*
 * CrossDatabaseQueryManager.java
 *
 * Generated on: 09/18/2014 09:40:57
 *
 */



package org.jdc.template.domain.attached.crossdatabasequery;


import org.jdc.template.domain.DatabaseManager;

import javax.inject.Inject;

@javax.inject.Singleton
public class CrossDatabaseQueryManager extends CrossDatabaseQueryBaseManager {

    @Inject
    public CrossDatabaseQueryManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public String getQuery() {
        return CrossDatabaseQuery.QUERY;
    }


}
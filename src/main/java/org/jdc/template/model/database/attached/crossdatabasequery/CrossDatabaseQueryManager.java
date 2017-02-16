/*
 * CrossDatabaseQueryManager.java
 *
 * Generated on: 09/18/2014 09:40:57
 *
 */



package org.jdc.template.model.database.attached.crossdatabasequery;


import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.main.individual.IndividualConst;

import javax.inject.Inject;

@javax.inject.Singleton
public class CrossDatabaseQueryManager extends CrossDatabaseQueryBaseManager {
    public static final String QUERY = "SELECT " +
            IndividualConst.FULL_C_ID + " AS " + CrossDatabaseQueryConst.C_ID + ", " +
            IndividualConst.FULL_C_FIRST_NAME + " AS " + CrossDatabaseQueryConst.C_NAME + ", " +
            " 1 " + " AS " + CrossDatabaseQueryConst.C_TYPE +
            " FROM " + IndividualConst.TABLE;

    @Inject
    public CrossDatabaseQueryManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public String getQuery() {
        return QUERY;
    }


}
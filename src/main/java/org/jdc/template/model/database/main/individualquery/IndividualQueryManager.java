/*
 * IndividualQueryManager.java
 *
 * Generated on: 02/11/2017 07:35:18
 *
 */



package org.jdc.template.model.database.main.individualquery;

import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.main.individual.IndividualConst;

@javax.inject.Singleton
public class IndividualQueryManager extends IndividualQueryBaseManager {

    public static final String QUERY =  "SELECT " +
            IndividualConst.FULL_C_ID + " AS " + IndividualQueryConst.C_ID + ", " +
            IndividualConst.FULL_C_FIRST_NAME + " AS " + IndividualQueryConst.C_NAME +
            " FROM " + IndividualConst.TABLE;

    @javax.inject.Inject
    public IndividualQueryManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public String getQuery() {
        return QUERY;
    }


}
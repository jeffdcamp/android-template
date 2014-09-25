/*
 * IndividualQueryManager.java
 *
 * Generated on: 09/17/2014 03:15:27
 *
 */



package org.company.project.domain.main.individualquery;


@javax.inject.Singleton
public class IndividualQueryManager extends IndividualQueryBaseManager {


    public IndividualQueryManager() {
    }

    @Override
    public String getQuery() {
        return IndividualQuery.QUERY;
    }


}
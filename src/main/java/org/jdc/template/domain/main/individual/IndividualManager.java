/*
 * IndividualManager.java
 *
 * Generated on: 10/02/2012 01:40:29
 *
 */



package org.jdc.template.domain.main.individual;


import org.jdc.template.domain.DatabaseManager;

import javax.inject.Inject;

@javax.inject.Singleton
public class IndividualManager extends IndividualBaseManager {

    @Inject
    public IndividualManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }


}
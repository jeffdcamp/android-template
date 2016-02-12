/*
 * IndividualListManager.java
 *
 * Generated on: 03/10/2014 11:15:44
 *
 */



package org.jdc.template.domain.other.individuallist;


import org.jdc.template.domain.DatabaseManager;

import javax.inject.Inject;

@javax.inject.Singleton
public class IndividualListManager extends IndividualListBaseManager {

    @Inject
    public IndividualListManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }


}
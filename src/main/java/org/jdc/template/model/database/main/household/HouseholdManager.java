/*
 * HouseholdManager.java
 *
 * Generated on: 10/14/2013 12:14:38
 *
 */



package org.jdc.template.model.database.main.household;


import org.jdc.template.model.database.DatabaseManager;

import javax.inject.Inject;

@javax.inject.Singleton
public class HouseholdManager extends HouseholdBaseManager {

    @Inject
    public HouseholdManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

}
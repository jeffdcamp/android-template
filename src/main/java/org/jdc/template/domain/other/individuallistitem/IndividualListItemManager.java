/*
 * IndividualListItemManager.java
 *
 * Generated on: 03/10/2014 11:15:44
 *
 */



package org.jdc.template.domain.other.individuallistitem;


import org.jdc.template.domain.DatabaseManager;

import javax.inject.Inject;

@javax.inject.Singleton
public class IndividualListItemManager extends IndividualListItemBaseManager {

    @Inject
    public IndividualListItemManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }


}
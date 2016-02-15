/*
 * IndividualListManager.kt
 *
 * Generated on: 02/14/2016 07:02:10
 *
 */



package org.jdc.template.domain.other.individuallist

import org.jdc.template.domain.DatabaseManager


@javax.inject.Singleton
class IndividualListManager : IndividualListBaseManager {


    @javax.inject.Inject
    constructor(databaseManager: DatabaseManager): super(databaseManager) {
    }


}
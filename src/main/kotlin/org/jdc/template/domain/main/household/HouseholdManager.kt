/*
 * HouseholdManager.kt
 *
 * Generated on: 02/14/2016 07:02:10
 *
 */



package org.jdc.template.domain.main.household

import org.jdc.template.domain.DatabaseManager


@javax.inject.Singleton
class HouseholdManager : HouseholdBaseManager {


    @javax.inject.Inject
    constructor(databaseManager: DatabaseManager): super(databaseManager) {
    }


}
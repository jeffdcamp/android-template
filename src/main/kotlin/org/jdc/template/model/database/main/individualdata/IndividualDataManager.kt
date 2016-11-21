/*
 * IndividualDataManager.kt
 *
 * Generated on: 11/20/2016 08:38:17
 *
 */



package org.jdc.template.model.database.main.individualdata

import org.jdc.template.model.database.DatabaseManager


@javax.inject.Singleton
class IndividualDataManager : IndividualDataBaseManager {


    @javax.inject.Inject
    constructor(databaseManager: DatabaseManager) : super(databaseManager) {
    }


}
/*
 * IndividualQueryManager.kt
 *
 * Generated on: 02/14/2016 07:02:10
 *
 */



package org.jdc.template.model.database.main.individualquery

import org.jdc.template.model.database.DatabaseManager


@javax.inject.Singleton
class IndividualQueryManager : IndividualQueryBaseManager {


    @javax.inject.Inject
    constructor(databaseManager: DatabaseManager): super(databaseManager) {
    }

    override fun getQuery(): String {
        return IndividualQuery.QUERY
    }


}
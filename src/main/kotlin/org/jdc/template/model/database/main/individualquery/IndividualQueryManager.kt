/*
 * IndividualQueryManager.kt
 *
 * Generated on: 02/11/2017 07:41:49
 *
 */



package org.jdc.template.model.database.main.individualquery

import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.model.database.main.individual.IndividualConst
import javax.inject.Inject

@javax.inject.Singleton
class IndividualQueryManager @Inject constructor(databaseManager: DatabaseManager) : IndividualQueryBaseManager(databaseManager) {

    companion object {
        val QUERY =  "SELECT " +
                IndividualConst.FULL_C_ID + " AS " + IndividualQueryConst.C_ID + ", " +
                IndividualConst.FULL_C_FIRST_NAME + " AS " + IndividualQueryConst.C_NAME +
                " FROM " + IndividualConst.TABLE
    }

    override fun getQuery() : String {
        return QUERY
    }


}
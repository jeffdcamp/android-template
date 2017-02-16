/*
 * CrossDatabaseQueryManager.kt
 *
 * Generated on: 02/11/2017 07:44:23
 *
 */



package org.jdc.template.model.database.attached.crossdatabasequery

import org.jdc.template.model.database.DatabaseManager
import javax.inject.Inject

@javax.inject.Singleton
class CrossDatabaseQueryManager @Inject constructor(databaseManager: DatabaseManager) : CrossDatabaseQueryBaseManager(databaseManager) {

    companion object {
        val QUERY =  "SELECT " +
            CrossDatabaseQueryConst.FULL_C_ID + " AS " + CrossDatabaseQueryConst.C_ID + ", " +
            CrossDatabaseQueryConst.FULL_C_NAME + " AS " + CrossDatabaseQueryConst.C_NAME + ", " +
            CrossDatabaseQueryConst.FULL_C_TYPE + " AS " + CrossDatabaseQueryConst.C_TYPE +
            " FROM SOME TABLE(S)"
    }

    override fun getQuery() : String {
        return QUERY
    }
}
/*
 * PhoneListViewManager.kt
 *
 * Generated on: 02/11/2017 07:41:49
 *
 */



package org.jdc.template.model.database.main.phonelistview

import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.model.database.main.individual.IndividualConst
import javax.inject.Inject

@javax.inject.Singleton
class PhoneListViewManager @Inject constructor(databaseManager: DatabaseManager) : PhoneListViewBaseManager(databaseManager) {

    companion object {
        val DROP_VIEW: String = "DROP VIEW IF EXISTS " + PhoneListViewConst.TABLE
        val CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + PhoneListViewConst.TABLE + " AS SELECT " +
                IndividualConst.FULL_C_ID + " AS " + PhoneListViewConst.C_ID + ", " +
                IndividualConst.FULL_C_FIRST_NAME + " AS " + PhoneListViewConst.C_NAME +
                " FROM " + IndividualConst.TABLE
    }


}
/*
 * PhoneListView.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.main.phonelistview

import android.database.Cursor
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.jdc.template.model.database.main.individual.IndividualConst

class PhoneListView : PhoneListViewBaseRecord {

    companion object {
        val DROP_VIEW: String
        val CREATE_VIEW: String

        init {
            DROP_VIEW = "DROP VIEW IF EXISTS " + PhoneListViewConst.TABLE + ";";
            CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + PhoneListViewConst.TABLE + " AS SELECT " +
                    IndividualConst.FULL_C_ID + " AS " + PhoneListViewConst.C_ID + ", " +
                    IndividualConst.FULL_C_LAST_NAME + " AS " + PhoneListViewConst.C_NAME +
                    " FROM " + IndividualConst.TABLE;
        }
    }

    constructor() {
    }

    constructor(record: PhoneListView) : super(record) {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: DBToolsContentValues<*>) {
        setContent(values)
    }

    fun getDropSql(): String {
        return DROP_VIEW
    }

    fun getCreateSql(): String {
        return CREATE_VIEW
    }


}
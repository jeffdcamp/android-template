/*
 * IndividualQuery.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.main.individualquery

import android.content.ContentValues
import android.database.Cursor
import org.dbtools.query.sql.SQLQueryBuilder

class IndividualQuery : IndividualQueryBaseRecord {

    companion object {
        val QUERY: String
        init {
            QUERY = SQLQueryBuilder()
                    .field(IndividualQueryConst.FULL_C_ID, IndividualQueryConst.C_ID)
                    .field(IndividualQueryConst.FULL_C_NAME, IndividualQueryConst.C_NAME)
                    .table("FROM SOME TABLE(S)")
                    .buildQuery();

        }
    }

    constructor() {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: ContentValues) {
        setContent(values)
    }


}
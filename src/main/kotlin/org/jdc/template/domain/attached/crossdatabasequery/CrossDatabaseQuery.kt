/*
 * CrossDatabaseQuery.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.domain.attached.crossdatabasequery

import android.content.ContentValues
import android.database.Cursor
import org.dbtools.query.sql.SQLQueryBuilder

class CrossDatabaseQuery : CrossDatabaseQueryBaseRecord {

    companion object {
        val QUERY: String
        init {
            QUERY = SQLQueryBuilder()
                    .field(CrossDatabaseQueryConst.FULL_C_ID, CrossDatabaseQueryConst.C_ID)
                    .field(CrossDatabaseQueryConst.FULL_C_NAME, CrossDatabaseQueryConst.C_NAME)
                    .field(CrossDatabaseQueryConst.FULL_C_TYPE, CrossDatabaseQueryConst.C_TYPE)
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
/*
 * IndividualQuery.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.main.individualquery

import android.database.Cursor
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
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

    constructor(record: IndividualQuery) : super(record) {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: DBToolsContentValues<*>) {
        setContent(values)
    }


}
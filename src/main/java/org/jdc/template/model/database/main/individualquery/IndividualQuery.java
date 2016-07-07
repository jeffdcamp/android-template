/*
 * IndividualQuery.java
 *
 * Created: 09/17/2014 03:15:27
 */



package org.jdc.template.model.database.main.individualquery;

import android.database.Cursor;
import android.util.Log;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import org.dbtools.query.sql.SQLQueryBuilder;
import org.jdc.template.model.database.main.individual.IndividualConst;

public class IndividualQuery extends IndividualQueryBaseRecord {

    public static final String QUERY;

    static {

        QUERY = new SQLQueryBuilder()
                .field(IndividualConst.FULL_C_ID, IndividualQueryConst.C_ID)
                .field(IndividualConst.FULL_C_FIRST_NAME, IndividualQueryConst.C_NAME)
                .table(IndividualConst.TABLE)
                .toString();

        Log.e("ldst", "QUERY: " + QUERY);
    }

    public IndividualQuery(IndividualQuery record) {
        super(record);
    }

    public IndividualQuery(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualQuery(DBToolsContentValues values) {
        setContent(values);
    }

    public IndividualQuery() {
    }


}
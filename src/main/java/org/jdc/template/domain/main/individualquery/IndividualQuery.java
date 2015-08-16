/*
 * IndividualQuery.java
 *
 * Created: 09/17/2014 03:15:27
 */



package org.jdc.template.domain.main.individualquery;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.jdc.template.domain.main.individual.Individual;
import org.dbtools.query.sql.SQLQueryBuilder;

public class IndividualQuery extends IndividualQueryBaseRecord {

    public static final String QUERY;

    static {

        QUERY = new SQLQueryBuilder()
                .field(Individual.FULL_C_ID, IndividualQuery.C_ID)
                .field(Individual.FULL_C_FIRST_NAME, IndividualQuery.C_NAME)
                .table(Individual.TABLE)
                .toString();

        Log.e("ldst", "QUERY: " + QUERY);
    }

    public IndividualQuery(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualQuery(ContentValues values) {
        setContent(values);
    }

    public IndividualQuery() {
    }


}
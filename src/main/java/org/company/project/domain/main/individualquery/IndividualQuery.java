/*
 * IndividualQuery.java
 *
 * Created: 09/17/2014 03:15:27
 */



package org.company.project.domain.main.individualquery;

import android.database.Cursor;
import android.content.ContentValues;

import org.company.project.domain.main.individual.Individual;

public class IndividualQuery extends IndividualQueryBaseRecord {

    public static final String QUERY = "(" +
            "SELECT " +
            Individual.FULL_C_ID + " AS " + IndividualQuery.C_ID + ", " +
            Individual.FULL_C_FIRST_NAME + " AS " + IndividualQuery.C_NAME +
            " FROM " + Individual.TABLE +
            ")";
    public static final String QUERY_RAW = "SELECT * FROM " + QUERY;

    public IndividualQuery(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualQuery(ContentValues values) {
        setContent(values);
    }

    public IndividualQuery() {
    }


}
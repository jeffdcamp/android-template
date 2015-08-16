/*
 * CrossDatabaseQuery.java
 *
 * Created: 09/18/2014 09:40:57
 */



package org.jdc.template.domain.attached.crossdatabasequery;

import android.content.ContentValues;
import android.database.Cursor;

import org.jdc.template.domain.main.individual.Individual;

public class CrossDatabaseQuery extends CrossDatabaseQueryBaseRecord {

    public static final String QUERY = "(" +
            "SELECT " +
            Individual.FULL_C_ID + " AS " + CrossDatabaseQuery.C_ID + ", " +
            Individual.FULL_C_FIRST_NAME + " AS " + CrossDatabaseQuery.C_NAME + ", " +
            " 1 " + " AS " + CrossDatabaseQuery.C_TYPE +
            " FROM " + Individual.TABLE +
            ")";
    public static final String QUERY_RAW = "SELECT * FROM " + QUERY;

    public CrossDatabaseQuery(Cursor cursor) {
        setContent(cursor);
    }

    public CrossDatabaseQuery(ContentValues values) {
        setContent(values);
    }

    public CrossDatabaseQuery() {
    }


}
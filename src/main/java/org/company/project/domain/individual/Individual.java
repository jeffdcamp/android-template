/*
 * Individual.java
 *
 * Created: 10/02/2012 01:40:29
 */



package org.company.project.domain.individual;

import android.database.Cursor;
import android.content.ContentValues;


public class Individual extends IndividualBaseRecord {


    public Individual(Cursor cursor) {
        setContent(cursor);
    }

    public Individual(ContentValues values) {
        setContent(values);
    }

    public Individual() {
    }


}
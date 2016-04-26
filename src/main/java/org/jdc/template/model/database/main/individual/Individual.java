/*
 * Individual.java
 *
 * Created: 10/02/2012 01:40:29
 */



package org.jdc.template.model.database.main.individual;

import android.database.Cursor;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;


public class Individual extends IndividualBaseRecord {


    public Individual(Cursor cursor) {
        setContent(cursor);
    }

    public Individual(DBToolsContentValues values) {
        setContent(values);
    }

    public Individual() {
    }



    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
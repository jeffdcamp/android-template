/*
 * Household.java
 *
 * Created: 10/14/2013 12:14:38
 */



package org.jdc.template.model.database.main.household;

import android.database.Cursor;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;


public class Household extends HouseholdBaseRecord {


    public Household(Cursor cursor) {
        setContent(cursor);
    }

    public Household(DBToolsContentValues values) {
        setContent(values);
    }

    public Household() {
    }


}
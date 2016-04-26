/*
 * IndividualList.java
 *
 * Created: 03/10/2014 11:15:44
 */



package org.jdc.template.model.database.other.individuallist;

import android.database.Cursor;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;


public class IndividualList extends IndividualListBaseRecord {


    public IndividualList(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualList(DBToolsContentValues values) {
        setContent(values);
    }

    public IndividualList() {
    }


}
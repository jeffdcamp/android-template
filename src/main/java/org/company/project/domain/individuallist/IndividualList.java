/*
 * IndividualList.java
 *
 * Created: 03/10/2014 11:15:44
 */



package org.company.project.domain.individuallist;

import android.database.Cursor;
import android.content.ContentValues;


public class IndividualList extends IndividualListBaseRecord {


    public IndividualList(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualList(ContentValues values) {
        setContent(values);
    }

    public IndividualList() {
    }


}
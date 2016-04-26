/*
 * IndividualListItem.java
 *
 * Created: 03/10/2014 11:15:44
 */



package org.jdc.template.model.database.other.individuallistitem;

import android.database.Cursor;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;


public class IndividualListItem extends IndividualListItemBaseRecord {


    public IndividualListItem(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualListItem(DBToolsContentValues values) {
        setContent(values);
    }

    public IndividualListItem() {
    }


}
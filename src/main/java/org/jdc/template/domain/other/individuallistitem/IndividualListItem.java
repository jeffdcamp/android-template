/*
 * IndividualListItem.java
 *
 * Created: 03/10/2014 11:15:44
 */



package org.jdc.template.domain.other.individuallistitem;

import android.database.Cursor;
import android.content.ContentValues;


public class IndividualListItem extends IndividualListItemBaseRecord {


    public IndividualListItem(Cursor cursor) {
        setContent(cursor);
    }

    public IndividualListItem(ContentValues values) {
        setContent(values);
    }

    public IndividualListItem() {
    }


}
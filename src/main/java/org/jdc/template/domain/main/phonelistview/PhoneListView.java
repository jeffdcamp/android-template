/*
 * PhoneListView.java
 *
 * Created: 04/01/2014 10:46:44
 */



package org.jdc.template.domain.main.phonelistview;

import android.database.Cursor;
import android.content.ContentValues;

import org.jdc.template.domain.main.individual.Individual;

public class PhoneListView extends PhoneListViewBaseRecord {

    public static final String DROP_VIEW = "DROP VIEW IF EXISTS " + PhoneListView.TABLE + ";";
    public static final String CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + PhoneListView.TABLE + " AS SELECT " +
            Individual.FULL_C_ID + " AS " + PhoneListView.C_ID + ", " +
            Individual.FULL_C_LAST_NAME + " AS " + PhoneListView.C_NAME +
            " FROM " + Individual.TABLE;

    public PhoneListView(Cursor cursor) {
        setContent(cursor);
    }

    public PhoneListView(ContentValues values) {
        setContent(values);
    }

    public PhoneListView() {
    }

    public String getDropSql() {
        return DROP_VIEW;
    }

    public String getCreateSql() {
        return CREATE_VIEW;
    }


}
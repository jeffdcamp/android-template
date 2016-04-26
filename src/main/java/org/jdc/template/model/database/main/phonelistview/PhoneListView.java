/*
 * PhoneListView.java
 *
 * Created: 04/01/2014 10:46:44
 */



package org.jdc.template.model.database.main.phonelistview;

import android.database.Cursor;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import org.jdc.template.model.database.main.individual.IndividualConst;

public class PhoneListView extends PhoneListViewBaseRecord {

    public static final String DROP_VIEW = "DROP VIEW IF EXISTS " + PhoneListViewConst.TABLE + ";";
    public static final String CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + PhoneListViewConst.TABLE + " AS SELECT " +
            IndividualConst.FULL_C_ID + " AS " + PhoneListViewConst.C_ID + ", " +
            IndividualConst.FULL_C_LAST_NAME + " AS " + PhoneListViewConst.C_NAME +
            " FROM " + IndividualConst.TABLE;

    public PhoneListView(Cursor cursor) {
        setContent(cursor);
    }

    public PhoneListView(DBToolsContentValues values) {
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
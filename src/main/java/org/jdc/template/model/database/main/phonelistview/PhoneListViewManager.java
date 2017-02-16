/*
 * PhoneListViewManager.java
 *
 * Generated on: 02/11/2017 07:35:18
 *
 */



package org.jdc.template.model.database.main.phonelistview;

import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.main.individual.IndividualConst;

@javax.inject.Singleton
public class PhoneListViewManager extends PhoneListViewBaseManager {

    public static final String DROP_VIEW = "DROP VIEW IF EXISTS " + PhoneListViewConst.TABLE + ";";;
    public static final String CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + PhoneListViewConst.TABLE + " AS SELECT " +
            IndividualConst.FULL_C_ID + " AS " + PhoneListViewConst.C_ID + ", " +
            IndividualConst.FULL_C_FIRST_NAME + " AS " + PhoneListViewConst.C_NAME +
            " FROM " + IndividualConst.TABLE;

    @javax.inject.Inject
    public PhoneListViewManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }


}
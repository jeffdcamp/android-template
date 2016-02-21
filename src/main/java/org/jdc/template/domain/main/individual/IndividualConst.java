/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individual;

import android.database.Cursor;

import org.dbtools.android.domain.DBToolsDateFormatter;
import org.jdc.template.domain.main.individualtype.IndividualType;


@SuppressWarnings("all")
public class IndividualConst {

    public static final String DATABASE = "main";
    public static final String TABLE = "INDIVIDUAL";
    public static final String FULL_TABLE = "main.INDIVIDUAL";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL._id";
    public static final String C_HOUSEHOLD_ID = "HOUSEHOLD_ID";
    public static final String FULL_C_HOUSEHOLD_ID = "INDIVIDUAL.HOUSEHOLD_ID";
    public static final String C_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE_ID";
    public static final String FULL_C_INDIVIDUAL_TYPE = "INDIVIDUAL.INDIVIDUAL_TYPE_ID";
    public static final String C_FIRST_NAME = "FIRST_NAME";
    public static final String FULL_C_FIRST_NAME = "INDIVIDUAL.FIRST_NAME";
    public static final String C_LAST_NAME = "LAST_NAME";
    public static final String FULL_C_LAST_NAME = "INDIVIDUAL.LAST_NAME";
    public static final String C_BIRTH_DATE = "BIRTH_DATE";
    public static final String FULL_C_BIRTH_DATE = "INDIVIDUAL.BIRTH_DATE";
    public static final String C_PHONE = "PHONE";
    public static final String FULL_C_PHONE = "INDIVIDUAL.PHONE";
    public static final String C_EMAIL = "EMAIL";
    public static final String FULL_C_EMAIL = "INDIVIDUAL.EMAIL";
    public static final String C_AVAILABLE = "AVAILABLE";
    public static final String FULL_C_AVAILABLE = "INDIVIDUAL.AVAILABLE";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "HOUSEHOLD_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_TYPE_ID INTEGER NOT NULL," + 
        "FIRST_NAME TEXT NOT NULL," + 
        "LAST_NAME TEXT NOT NULL," + 
        "BIRTH_DATE INTEGER," + 
        "PHONE TEXT NOT NULL," + 
        "EMAIL TEXT NOT NULL," + 
        "AVAILABLE INTEGER NOT NULL," + 
        "FOREIGN KEY (HOUSEHOLD_ID) REFERENCES HOUSEHOLD (_id)," + 
        "FOREIGN KEY (INDIVIDUAL_TYPE_ID) REFERENCES INDIVIDUAL_TYPE (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL;";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_HOUSEHOLD_ID,
        C_INDIVIDUAL_TYPE,
        C_FIRST_NAME,
        C_LAST_NAME,
        C_BIRTH_DATE,
        C_PHONE,
        C_EMAIL,
        C_AVAILABLE};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_HOUSEHOLD_ID,
        FULL_C_INDIVIDUAL_TYPE,
        FULL_C_FIRST_NAME,
        FULL_C_LAST_NAME,
        FULL_C_BIRTH_DATE,
        FULL_C_PHONE,
        FULL_C_EMAIL,
        FULL_C_AVAILABLE};

    public IndividualConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static long getHouseholdId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_HOUSEHOLD_ID));
    }

    public static IndividualType getIndividualType(Cursor cursor) {
        return IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE))];
    }

    public static String getFirstName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_FIRST_NAME));
    }

    public static String getLastName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_LAST_NAME));
    }

    public static org.joda.time.DateTime getBirthDate(Cursor cursor) {
        return !cursor.isNull(cursor.getColumnIndexOrThrow(C_BIRTH_DATE)) ? DBToolsDateFormatter.longToDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) : null;
    }

    public static String getPhone(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE));
    }

    public static String getEmail(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_EMAIL));
    }

    public static boolean isAvailable(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_AVAILABLE)) != 0 ? true : false;
    }


}
/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individual;

import android.database.Cursor;


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
    public static final String C_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE";
    public static final String FULL_C_INDIVIDUAL_TYPE = "INDIVIDUAL.INDIVIDUAL_TYPE";
    public static final String C_INDIVIDUAL_TYPE_TEXT = "INDIVIDUAL_TYPE_TEXT";
    public static final String FULL_C_INDIVIDUAL_TYPE_TEXT = "INDIVIDUAL.INDIVIDUAL_TYPE_TEXT";
    public static final String C_FIRST_NAME = "FIRST_NAME";
    public static final String FULL_C_FIRST_NAME = "INDIVIDUAL.FIRST_NAME";
    public static final String C_LAST_NAME = "LAST_NAME";
    public static final String FULL_C_LAST_NAME = "INDIVIDUAL.LAST_NAME";
    public static final String C_BIRTH_DATE = "BIRTH_DATE";
    public static final String FULL_C_BIRTH_DATE = "INDIVIDUAL.BIRTH_DATE";
    public static final String C_ALARM_TIME = "ALARM_TIME";
    public static final String FULL_C_ALARM_TIME = "INDIVIDUAL.ALARM_TIME";
    public static final String C_LAST_MODIFIED = "LAST_MODIFIED";
    public static final String FULL_C_LAST_MODIFIED = "INDIVIDUAL.LAST_MODIFIED";
    public static final String C_SAMPLE_DATE_TIME = "SAMPLE_DATE_TIME";
    public static final String FULL_C_SAMPLE_DATE_TIME = "INDIVIDUAL.SAMPLE_DATE_TIME";
    public static final String C_SAMPLE_TIMESTAMP = "SAMPLE_TIMESTAMP";
    public static final String FULL_C_SAMPLE_TIMESTAMP = "INDIVIDUAL.SAMPLE_TIMESTAMP";
    public static final String C_PHONE = "PHONE";
    public static final String FULL_C_PHONE = "INDIVIDUAL.PHONE";
    public static final String C_EMAIL = "EMAIL";
    public static final String FULL_C_EMAIL = "INDIVIDUAL.EMAIL";
    public static final String C_AVAILABLE = "AVAILABLE";
    public static final String FULL_C_AVAILABLE = "INDIVIDUAL.AVAILABLE";
    public static final String C_AMOUNT1 = "AMOUNT1";
    public static final String FULL_C_AMOUNT1 = "INDIVIDUAL.AMOUNT1";
    public static final String C_AMOUNT2 = "AMOUNT2";
    public static final String FULL_C_AMOUNT2 = "INDIVIDUAL.AMOUNT2";
    public static final String C_ENABLED = "ENABLED";
    public static final String FULL_C_ENABLED = "INDIVIDUAL.ENABLED";
    public static final String C_SPOUSE_INDIVIDUAL_ID = "SPOUSE_INDIVIDUAL_ID";
    public static final String FULL_C_SPOUSE_INDIVIDUAL_ID = "INDIVIDUAL.SPOUSE_INDIVIDUAL_ID";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "HOUSEHOLD_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_TYPE INTEGER NOT NULL," + 
        "INDIVIDUAL_TYPE_TEXT TEXT NOT NULL," + 
        "FIRST_NAME TEXT NOT NULL," + 
        "LAST_NAME TEXT NOT NULL," + 
        "BIRTH_DATE TEXT," + 
        "ALARM_TIME TEXT NOT NULL," + 
        "LAST_MODIFIED INTEGER NOT NULL," + 
        "SAMPLE_DATE_TIME TEXT," + 
        "SAMPLE_TIMESTAMP INTEGER," + 
        "PHONE TEXT NOT NULL," + 
        "EMAIL TEXT NOT NULL," + 
        "AVAILABLE INTEGER NOT NULL," + 
        "AMOUNT1 REAL NOT NULL," + 
        "AMOUNT2 REAL NOT NULL," + 
        "ENABLED INTEGER NOT NULL," + 
        "SPOUSE_INDIVIDUAL_ID INTEGER," + 
        "FOREIGN KEY (HOUSEHOLD_ID) REFERENCES HOUSEHOLD (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL;";
    public static final String INSERT_STATEMENT = "INSERT INTO INDIVIDUAL (HOUSEHOLD_ID,INDIVIDUAL_TYPE,INDIVIDUAL_TYPE_TEXT,FIRST_NAME,LAST_NAME,BIRTH_DATE,ALARM_TIME,LAST_MODIFIED,SAMPLE_DATE_TIME,SAMPLE_TIMESTAMP,PHONE,EMAIL,AVAILABLE,AMOUNT1,AMOUNT2,ENABLED,SPOUSE_INDIVIDUAL_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_STATEMENT = "UPDATE INDIVIDUAL SET HOUSEHOLD_ID=?, INDIVIDUAL_TYPE=?, INDIVIDUAL_TYPE_TEXT=?, FIRST_NAME=?, LAST_NAME=?, BIRTH_DATE=?, ALARM_TIME=?, LAST_MODIFIED=?, SAMPLE_DATE_TIME=?, SAMPLE_TIMESTAMP=?, PHONE=?, EMAIL=?, AVAILABLE=?, AMOUNT1=?, AMOUNT2=?, ENABLED=?, SPOUSE_INDIVIDUAL_ID=? WHERE _id = ?";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_HOUSEHOLD_ID,
        C_INDIVIDUAL_TYPE,
        C_INDIVIDUAL_TYPE_TEXT,
        C_FIRST_NAME,
        C_LAST_NAME,
        C_BIRTH_DATE,
        C_ALARM_TIME,
        C_LAST_MODIFIED,
        C_SAMPLE_DATE_TIME,
        C_SAMPLE_TIMESTAMP,
        C_PHONE,
        C_EMAIL,
        C_AVAILABLE,
        C_AMOUNT1,
        C_AMOUNT2,
        C_ENABLED,
        C_SPOUSE_INDIVIDUAL_ID};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_HOUSEHOLD_ID,
        FULL_C_INDIVIDUAL_TYPE,
        FULL_C_INDIVIDUAL_TYPE_TEXT,
        FULL_C_FIRST_NAME,
        FULL_C_LAST_NAME,
        FULL_C_BIRTH_DATE,
        FULL_C_ALARM_TIME,
        FULL_C_LAST_MODIFIED,
        FULL_C_SAMPLE_DATE_TIME,
        FULL_C_SAMPLE_TIMESTAMP,
        FULL_C_PHONE,
        FULL_C_EMAIL,
        FULL_C_AVAILABLE,
        FULL_C_AMOUNT1,
        FULL_C_AMOUNT2,
        FULL_C_ENABLED,
        FULL_C_SPOUSE_INDIVIDUAL_ID};

    public IndividualConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static long getHouseholdId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_HOUSEHOLD_ID));
    }

    public static org.jdc.template.model.type.IndividualType getIndividualType(Cursor cursor) {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.IndividualType.class, cursor.getInt(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE)), org.jdc.template.model.type.IndividualType.HEAD);
    }

    public static org.jdc.template.model.type.IndividualType getIndividualTypeText(Cursor cursor) {
        return org.dbtools.android.domain.util.EnumUtil.stringToEnum(org.jdc.template.model.type.IndividualType.class, cursor.getString(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE_TEXT)), org.jdc.template.model.type.IndividualType.HEAD);
    }

    public static String getFirstName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_FIRST_NAME));
    }

    public static String getLastName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_LAST_NAME));
    }

    public static org.threeten.bp.LocalDate getBirthDate(Cursor cursor) {
        return org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDate(cursor.getString(cursor.getColumnIndexOrThrow(C_BIRTH_DATE)));
    }

    public static org.threeten.bp.LocalTime getAlarmTime(Cursor cursor) {
        return org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalTime(cursor.getString(cursor.getColumnIndexOrThrow(C_ALARM_TIME)));
    }

    public static org.threeten.bp.LocalDateTime getLastModified(Cursor cursor) {
        return !cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)) ? org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) : null;
    }

    public static org.threeten.bp.LocalDateTime getSampleDateTime(Cursor cursor) {
        return org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow(C_SAMPLE_DATE_TIME)));
    }

    public static org.threeten.bp.LocalDateTime getSampleTimestamp(Cursor cursor) {
        return !cursor.isNull(cursor.getColumnIndexOrThrow(C_SAMPLE_TIMESTAMP)) ? org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_SAMPLE_TIMESTAMP))) : null;
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

    public static float getAmount1(Cursor cursor) {
        return cursor.getFloat(cursor.getColumnIndexOrThrow(C_AMOUNT1));
    }

    public static double getAmount2(Cursor cursor) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(C_AMOUNT2));
    }

    public static boolean isEnabled(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ENABLED)) != 0 ? true : false;
    }

    public static Long getSpouseIndividualId(Cursor cursor) {
        return !cursor.isNull(cursor.getColumnIndexOrThrow(C_SPOUSE_INDIVIDUAL_ID)) ? cursor.getLong(cursor.getColumnIndexOrThrow(C_SPOUSE_INDIVIDUAL_ID)) : null;
    }


}
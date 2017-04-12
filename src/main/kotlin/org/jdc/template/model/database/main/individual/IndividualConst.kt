/*
 * IndividualBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individual

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object IndividualConst  {

     const val DATABASE = "main"
     const val TABLE = "INDIVIDUAL"
     const val FULL_TABLE = "main.INDIVIDUAL"
     const val PRIMARY_KEY_COLUMN = "_id"
     const val C_ID = "_id"
     const val FULL_C_ID = "INDIVIDUAL._id"
     const val C_HOUSEHOLD_ID = "HOUSEHOLD_ID"
     const val FULL_C_HOUSEHOLD_ID = "INDIVIDUAL.HOUSEHOLD_ID"
     const val C_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE"
     const val FULL_C_INDIVIDUAL_TYPE = "INDIVIDUAL.INDIVIDUAL_TYPE"
     const val C_INDIVIDUAL_TYPE_TEXT = "INDIVIDUAL_TYPE_TEXT"
     const val FULL_C_INDIVIDUAL_TYPE_TEXT = "INDIVIDUAL.INDIVIDUAL_TYPE_TEXT"
     const val C_FIRST_NAME = "FIRST_NAME"
     const val FULL_C_FIRST_NAME = "INDIVIDUAL.FIRST_NAME"
     const val C_LAST_NAME = "LAST_NAME"
     const val FULL_C_LAST_NAME = "INDIVIDUAL.LAST_NAME"
     const val C_BIRTH_DATE = "BIRTH_DATE"
     const val FULL_C_BIRTH_DATE = "INDIVIDUAL.BIRTH_DATE"
     const val C_ALARM_TIME = "ALARM_TIME"
     const val FULL_C_ALARM_TIME = "INDIVIDUAL.ALARM_TIME"
     const val C_LAST_MODIFIED = "LAST_MODIFIED"
     const val FULL_C_LAST_MODIFIED = "INDIVIDUAL.LAST_MODIFIED"
     const val C_SAMPLE_DATE_TIME = "SAMPLE_DATE_TIME"
     const val FULL_C_SAMPLE_DATE_TIME = "INDIVIDUAL.SAMPLE_DATE_TIME"
     const val C_SAMPLE_TIMESTAMP = "SAMPLE_TIMESTAMP"
     const val FULL_C_SAMPLE_TIMESTAMP = "INDIVIDUAL.SAMPLE_TIMESTAMP"
     const val C_PHONE = "PHONE"
     const val FULL_C_PHONE = "INDIVIDUAL.PHONE"
     const val C_EMAIL = "EMAIL"
     const val FULL_C_EMAIL = "INDIVIDUAL.EMAIL"
     const val C_AVAILABLE = "AVAILABLE"
     const val FULL_C_AVAILABLE = "INDIVIDUAL.AVAILABLE"
     const val C_AMOUNT1 = "AMOUNT1"
     const val FULL_C_AMOUNT1 = "INDIVIDUAL.AMOUNT1"
     const val C_AMOUNT2 = "AMOUNT2"
     const val FULL_C_AMOUNT2 = "INDIVIDUAL.AMOUNT2"
     const val C_ENABLED = "ENABLED"
     const val FULL_C_ENABLED = "INDIVIDUAL.ENABLED"
     const val C_SPOUSE_INDIVIDUAL_ID = "SPOUSE_INDIVIDUAL_ID"
     const val FULL_C_SPOUSE_INDIVIDUAL_ID = "INDIVIDUAL.SPOUSE_INDIVIDUAL_ID"
     const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL (" + 
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
        ""
     const val DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL;"
     const val INSERT_STATEMENT = "INSERT INTO INDIVIDUAL (HOUSEHOLD_ID,INDIVIDUAL_TYPE,INDIVIDUAL_TYPE_TEXT,FIRST_NAME,LAST_NAME,BIRTH_DATE,ALARM_TIME,LAST_MODIFIED,SAMPLE_DATE_TIME,SAMPLE_TIMESTAMP,PHONE,EMAIL,AVAILABLE,AMOUNT1,AMOUNT2,ENABLED,SPOUSE_INDIVIDUAL_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
     const val UPDATE_STATEMENT = "UPDATE INDIVIDUAL SET HOUSEHOLD_ID=?, INDIVIDUAL_TYPE=?, INDIVIDUAL_TYPE_TEXT=?, FIRST_NAME=?, LAST_NAME=?, BIRTH_DATE=?, ALARM_TIME=?, LAST_MODIFIED=?, SAMPLE_DATE_TIME=?, SAMPLE_TIMESTAMP=?, PHONE=?, EMAIL=?, AVAILABLE=?, AMOUNT1=?, AMOUNT2=?, ENABLED=?, SPOUSE_INDIVIDUAL_ID=? WHERE _id = ?"
     val ALL_COLUMNS = arrayOf(
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
        C_SPOUSE_INDIVIDUAL_ID)
     val ALL_COLUMNS_FULL = arrayOf(
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
        FULL_C_SPOUSE_INDIVIDUAL_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getHouseholdId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_HOUSEHOLD_ID))
    }

    fun getIndividualType(cursor: Cursor) : org.jdc.template.model.type.IndividualType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.IndividualType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE)), org.jdc.template.model.type.IndividualType.HEAD)
    }

    fun getIndividualTypeText(cursor: Cursor) : org.jdc.template.model.type.IndividualType {
        return org.dbtools.android.domain.util.EnumUtil.stringToEnum(org.jdc.template.model.type.IndividualType::class.java, cursor.getString(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE_TEXT)), org.jdc.template.model.type.IndividualType.HEAD)
    }

    fun getFirstName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_FIRST_NAME))
    }

    fun getLastName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_LAST_NAME))
    }

    fun getBirthDate(cursor: Cursor) : org.threeten.bp.LocalDate? {
        return org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDate(cursor.getString(cursor.getColumnIndexOrThrow(C_BIRTH_DATE)))
    }

    fun getAlarmTime(cursor: Cursor) : org.threeten.bp.LocalTime {
        return org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalTime(cursor.getString(cursor.getColumnIndexOrThrow(C_ALARM_TIME)))!!
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }

    fun getSampleDateTime(cursor: Cursor) : org.threeten.bp.LocalDateTime? {
        return org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow(C_SAMPLE_DATE_TIME)))
    }

    fun getSampleTimestamp(cursor: Cursor) : org.threeten.bp.LocalDateTime? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SAMPLE_TIMESTAMP))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_SAMPLE_TIMESTAMP))) else null
    }

    fun getPhone(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE))
    }

    fun getEmail(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_EMAIL))
    }

    fun isAvailable(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_AVAILABLE)) != 0
    }

    fun getAmount1(cursor: Cursor) : Float {
        return cursor.getFloat(cursor.getColumnIndexOrThrow(C_AMOUNT1))
    }

    fun getAmount2(cursor: Cursor) : Double {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(C_AMOUNT2))
    }

    fun isEnabled(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ENABLED)) != 0
    }

    fun getSpouseIndividualId(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SPOUSE_INDIVIDUAL_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(C_SPOUSE_INDIVIDUAL_ID)) else null
    }


}
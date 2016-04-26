/*
 * IndividualBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individual

import android.database.Cursor
import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.jdc.template.model.database.main.individualtype.IndividualType


@SuppressWarnings("all")
abstract class IndividualBaseRecord : AndroidBaseRecord() {

     var id: Long = 0
     var householdId: Long = 0
     var individualType: IndividualType = IndividualType.HEAD
     var firstName: String = ""
     var lastName: String = ""
     var birthDate: org.threeten.bp.LocalDate? = null
     var alarmTime: org.threeten.bp.LocalTime = org.threeten.bp.LocalTime.now()
     var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()
     var sampleDateTime: org.threeten.bp.LocalDateTime? = null
     var sampleTimestamp: org.threeten.bp.LocalDateTime? = null
     var phone: String = ""
     var email: String = ""
     var available: Boolean = false
     var spouseIndividualId: Long? = 0

    override fun getIdColumnName(): String {
        return IndividualConst.C_ID
    }

    override fun getPrimaryKeyId(): Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns(): Array<String> {
        return IndividualConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return IndividualConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(IndividualConst.C_HOUSEHOLD_ID, householdId)
        values.put(IndividualConst.C_INDIVIDUAL_TYPE, individualType.ordinal)
        values.put(IndividualConst.C_FIRST_NAME, firstName)
        values.put(IndividualConst.C_LAST_NAME, lastName)
        values.put(IndividualConst.C_BIRTH_DATE, org.dbtools.android.domain.DBToolsDateFormatter.localDateToDBString(birthDate))
        values.put(IndividualConst.C_ALARM_TIME, org.dbtools.android.domain.DBToolsDateFormatter.localTimeToDBString(alarmTime))
        values.put(IndividualConst.C_LAST_MODIFIED, org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(lastModified))
        values.put(IndividualConst.C_SAMPLE_DATE_TIME, org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToDBString(sampleDateTime))
        values.put(IndividualConst.C_SAMPLE_TIMESTAMP, org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(sampleTimestamp))
        values.put(IndividualConst.C_PHONE, phone)
        values.put(IndividualConst.C_EMAIL, email)
        values.put(IndividualConst.C_AVAILABLE, if (available) 1 else 0)
        values.put(IndividualConst.C_SPOUSE_INDIVIDUAL_ID, spouseIndividualId)
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            householdId,
            individualType.ordinal,
            firstName,
            lastName,
            org.dbtools.android.domain.DBToolsDateFormatter.localDateToDBString(birthDate),
            org.dbtools.android.domain.DBToolsDateFormatter.localTimeToDBString(alarmTime),
            org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(lastModified),
            org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToDBString(sampleDateTime),
            org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(sampleTimestamp),
            phone,
            email,
            if (available) 1 else 0,
            spouseIndividualId)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        householdId = values.getAsLong(IndividualConst.C_HOUSEHOLD_ID)
        individualType = IndividualType.values()[values.getAsInteger(IndividualConst.C_INDIVIDUAL_TYPE)]
        firstName = values.getAsString(IndividualConst.C_FIRST_NAME)
        lastName = values.getAsString(IndividualConst.C_LAST_NAME)
        birthDate = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDate(values.getAsString(IndividualConst.C_BIRTH_DATE))
        alarmTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalTime(values.getAsString(IndividualConst.C_ALARM_TIME))!!
        lastModified = org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_LAST_MODIFIED))!!
        sampleDateTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDateTime(values.getAsString(IndividualConst.C_SAMPLE_DATE_TIME))
        sampleTimestamp = org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_SAMPLE_TIMESTAMP))
        phone = values.getAsString(IndividualConst.C_PHONE)
        email = values.getAsString(IndividualConst.C_EMAIL)
        available = values.getAsBoolean(IndividualConst.C_AVAILABLE)
        spouseIndividualId = values.getAsLong(IndividualConst.C_SPOUSE_INDIVIDUAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_ID))
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_HOUSEHOLD_ID))
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_INDIVIDUAL_TYPE))]
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_FIRST_NAME))
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_NAME))
        birthDate = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDate(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_BIRTH_DATE)))
        alarmTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_ALARM_TIME)))!!
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED))) org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED)))!! else null!!
        sampleDateTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_DATE_TIME)))
        sampleTimestamp = if (!cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP))) org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP))) else null
        phone = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_PHONE))
        email = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_EMAIL))
        available = if (cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_AVAILABLE)) != 0) true else false
        spouseIndividualId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SPOUSE_INDIVIDUAL_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SPOUSE_INDIVIDUAL_ID)) else null
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
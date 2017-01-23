/*
 * IndividualBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individual

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.jdc.template.model.database.main.individualtype.IndividualType
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody")
@SuppressWarnings("all")
abstract class IndividualBaseRecord : AndroidBaseRecord {

     open var id: Long = 0
     open var householdId: Long = 0
     open var individualType: IndividualType = IndividualType.HEAD
     open var firstName: String = ""
     open var lastName: String = ""
     open var birthDate: org.threeten.bp.LocalDate? = null
     open var alarmTime: org.threeten.bp.LocalTime = org.threeten.bp.LocalTime.now()
     open var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()
     open var sampleDateTime: org.threeten.bp.LocalDateTime? = null
     open var sampleTimestamp: org.threeten.bp.LocalDateTime? = null
     open var phone: String = ""
     open var email: String = ""
     open var available: Boolean = false
     open var amount1: Float = 0.0f
     open var amount2: Double = 0.0
     open var enabled: Boolean = false
     open var spouseIndividualId: Long? = 0

    constructor(record: Individual) {
        this.householdId = record.householdId
        this.individualType = record.individualType
        this.firstName = record.firstName
        this.lastName = record.lastName
        this.birthDate = record.birthDate
        this.alarmTime = record.alarmTime
        this.lastModified = record.lastModified
        this.sampleDateTime = record.sampleDateTime
        this.sampleTimestamp = record.sampleTimestamp
        this.phone = record.phone
        this.email = record.email
        this.available = record.available
        this.amount1 = record.amount1
        this.amount2 = record.amount2
        this.enabled = record.enabled
        this.spouseIndividualId = record.spouseIndividualId
    }

    constructor() {
    }

    override fun getIdColumnName() : String {
        return IndividualConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return IndividualConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return IndividualConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(IndividualConst.C_HOUSEHOLD_ID, householdId)
        values.put(IndividualConst.C_INDIVIDUAL_TYPE, individualType.ordinal.toLong())
        values.put(IndividualConst.C_FIRST_NAME, firstName)
        values.put(IndividualConst.C_LAST_NAME, lastName)
        values.put(IndividualConst.C_BIRTH_DATE, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate))
        values.put(IndividualConst.C_ALARM_TIME, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime)!!)
        values.put(IndividualConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        values.put(IndividualConst.C_SAMPLE_DATE_TIME, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime))
        values.put(IndividualConst.C_SAMPLE_TIMESTAMP, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp))
        values.put(IndividualConst.C_PHONE, phone)
        values.put(IndividualConst.C_EMAIL, email)
        values.put(IndividualConst.C_AVAILABLE, if (available) 1L else 0L)
        values.put(IndividualConst.C_AMOUNT1, amount1.toDouble())
        values.put(IndividualConst.C_AMOUNT2, amount2)
        values.put(IndividualConst.C_ENABLED, if (enabled) 1L else 0L)
        values.put(IndividualConst.C_SPOUSE_INDIVIDUAL_ID, spouseIndividualId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            householdId,
            individualType.ordinal.toLong(),
            firstName,
            lastName,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime)!!,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp),
            phone,
            email,
            if (available) 1L else 0L,
            amount1.toDouble(),
            amount2,
            if (enabled) 1L else 0L,
            spouseIndividualId)
    }

    open fun copy() : Individual {
        val copy = Individual()
        copy.id = id
        copy.householdId = householdId
        copy.individualType = individualType
        copy.firstName = firstName
        copy.lastName = lastName
        copy.birthDate = birthDate
        copy.alarmTime = alarmTime
        copy.lastModified = lastModified
        copy.sampleDateTime = sampleDateTime
        copy.sampleTimestamp = sampleTimestamp
        copy.phone = phone
        copy.email = email
        copy.available = available
        copy.amount1 = amount1
        copy.amount2 = amount2
        copy.enabled = enabled
        copy.spouseIndividualId = spouseIndividualId
        return copy
    }

    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, householdId)
        statement.bindLong(2, individualType.ordinal.toLong())
        statement.bindString(3, firstName)
        statement.bindString(4, lastName)
        if (birthDate != null) {
            statement.bindString(5, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate)!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindString(6, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime)!!)
        statement.bindLong(7, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        if (sampleDateTime != null) {
            statement.bindString(8, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime)!!)
        } else {
            statement.bindNull(8)
        }
        if (sampleTimestamp != null) {
            statement.bindLong(9, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp)!!)
        } else {
            statement.bindNull(9)
        }
        statement.bindString(10, phone)
        statement.bindString(11, email)
        statement.bindLong(12, if (available) 1L else 0L)
        statement.bindDouble(13, amount1.toDouble())
        statement.bindDouble(14, amount2)
        statement.bindLong(15, if (enabled) 1L else 0L)
        if (spouseIndividualId != null) {
            statement.bindLong(16, spouseIndividualId!!)
        } else {
            statement.bindNull(16)
        }
    }

    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, householdId)
        statement.bindLong(2, individualType.ordinal.toLong())
        statement.bindString(3, firstName)
        statement.bindString(4, lastName)
        if (birthDate != null) {
            statement.bindString(5, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate)!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindString(6, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime)!!)
        statement.bindLong(7, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        if (sampleDateTime != null) {
            statement.bindString(8, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime)!!)
        } else {
            statement.bindNull(8)
        }
        if (sampleTimestamp != null) {
            statement.bindLong(9, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp)!!)
        } else {
            statement.bindNull(9)
        }
        statement.bindString(10, phone)
        statement.bindString(11, email)
        statement.bindLong(12, if (available) 1L else 0L)
        statement.bindDouble(13, amount1.toDouble())
        statement.bindDouble(14, amount2)
        statement.bindLong(15, if (enabled) 1L else 0L)
        if (spouseIndividualId != null) {
            statement.bindLong(16, spouseIndividualId!!)
        } else {
            statement.bindNull(16)
        }
        statement.bindLong(17, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        householdId = values.getAsLong(IndividualConst.C_HOUSEHOLD_ID)
        individualType = IndividualType.values()[values.getAsInteger(IndividualConst.C_INDIVIDUAL_TYPE)]
        firstName = values.getAsString(IndividualConst.C_FIRST_NAME)
        lastName = values.getAsString(IndividualConst.C_LAST_NAME)
        birthDate = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDate(values.getAsString(IndividualConst.C_BIRTH_DATE))
        alarmTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalTime(values.getAsString(IndividualConst.C_ALARM_TIME))!!
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_LAST_MODIFIED))!!
        sampleDateTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDateTime(values.getAsString(IndividualConst.C_SAMPLE_DATE_TIME))
        sampleTimestamp = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_SAMPLE_TIMESTAMP))
        phone = values.getAsString(IndividualConst.C_PHONE)
        email = values.getAsString(IndividualConst.C_EMAIL)
        available = values.getAsBoolean(IndividualConst.C_AVAILABLE)
        amount1 = values.getAsFloat(IndividualConst.C_AMOUNT1)
        amount2 = values.getAsDouble(IndividualConst.C_AMOUNT2)
        enabled = values.getAsBoolean(IndividualConst.C_ENABLED)
        spouseIndividualId = values.getAsLong(IndividualConst.C_SPOUSE_INDIVIDUAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_ID))
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_HOUSEHOLD_ID))
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_INDIVIDUAL_TYPE))]
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_FIRST_NAME))
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_NAME))
        birthDate = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDate(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_BIRTH_DATE)))
        alarmTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_ALARM_TIME)))!!
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED)))!! else null!!
        sampleDateTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_DATE_TIME)))
        sampleTimestamp = if (!cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP))) else null
        phone = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_PHONE))
        email = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_EMAIL))
        available = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_AVAILABLE)) != 0
        amount1 = cursor.getFloat(cursor.getColumnIndexOrThrow(IndividualConst.C_AMOUNT1))
        amount2 = cursor.getDouble(cursor.getColumnIndexOrThrow(IndividualConst.C_AMOUNT2))
        enabled = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_ENABLED)) != 0
        spouseIndividualId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SPOUSE_INDIVIDUAL_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SPOUSE_INDIVIDUAL_ID)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.individual

import android.content.ContentValues
import android.database.Cursor

import org.dbtools.android.domain.AndroidBaseRecord
import org.jdc.template.domain.main.individualtype.IndividualType


@SuppressWarnings("all")
abstract class KotlinIndividualBaseRecord : AndroidBaseRecord() {
    var id: Long = 0
    var householdId: Long = 0
    var individualType = IndividualType.HEAD
    var firstName = ""
    var lastName = ""
    var birthDate: org.joda.time.DateTime? = null
    var phone = ""
    var email = ""
    var isAvailable = false

    override fun getIdColumnName(): String {
        return C_ID
    }

    override fun getPrimaryKeyId(): Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllKeys(): Array<String> {
        return ALL_KEYS.clone()
    }

    override fun getContentValues(): ContentValues {
        val values = ContentValues()
        values.put(C_HOUSEHOLD_ID, householdId)
        values.put(C_INDIVIDUAL_TYPE, individualType.ordinal)
        values.put(C_FIRST_NAME, firstName)
        values.put(C_LAST_NAME, lastName)
        values.put(C_BIRTH_DATE, if (birthDate != null) birthDate!!.millis else null)
        values.put(C_PHONE, phone)
        values.put(C_EMAIL, email)
        values.put(C_AVAILABLE, if (isAvailable) 1 else 0)
        return values
    }

    override fun getValues(): Array<Any?> {
        val values = arrayOf<Any?>(id, householdId,
                individualType.ordinal,
                firstName, lastName,
                if (birthDate != null) birthDate!!.millis else null,
                phone, email,
                if (isAvailable) 1 else 0)
        return values
    }

    fun setContent(values: ContentValues) {
        householdId = values.getAsLong(C_HOUSEHOLD_ID)!!
        individualType = IndividualType.values()[values.getAsInteger(C_INDIVIDUAL_TYPE)]
        firstName = values.getAsString(C_FIRST_NAME)
        lastName = values.getAsString(C_LAST_NAME)
        birthDate = org.joda.time.DateTime(values.getAsLong(C_BIRTH_DATE))
        phone = values.getAsString(C_PHONE)
        email = values.getAsString(C_EMAIL)
        isAvailable = values.getAsBoolean(C_AVAILABLE)!!
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(C_HOUSEHOLD_ID))
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE))]
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(C_FIRST_NAME))
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(C_LAST_NAME))
        birthDate = if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) org.joda.time.DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) else null
        phone = cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE))
        email = cursor.getString(cursor.getColumnIndexOrThrow(C_EMAIL))
        isAvailable = if (cursor.getInt(cursor.getColumnIndexOrThrow(C_AVAILABLE)) != 0) true else false
    }

    override fun isNewRecord() = primaryKeyId <= 0

    companion object {
        val DATABASE = "main"
        val TABLE = "INDIVIDUAL"
        val FULL_TABLE = "main.INDIVIDUAL"
        val PRIMARY_KEY_COLUMN = "_id"
        val C_ID = "_id"
        val FULL_C_ID = "INDIVIDUAL._id"
        val C_HOUSEHOLD_ID = "HOUSEHOLD_ID"
        val FULL_C_HOUSEHOLD_ID = "INDIVIDUAL.HOUSEHOLD_ID"
        val C_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE_ID"
        val FULL_C_INDIVIDUAL_TYPE = "INDIVIDUAL.INDIVIDUAL_TYPE_ID"
        val C_FIRST_NAME = "FIRST_NAME"
        val FULL_C_FIRST_NAME = "INDIVIDUAL.FIRST_NAME"
        val C_LAST_NAME = "LAST_NAME"
        val FULL_C_LAST_NAME = "INDIVIDUAL.LAST_NAME"
        val C_BIRTH_DATE = "BIRTH_DATE"
        val FULL_C_BIRTH_DATE = "INDIVIDUAL.BIRTH_DATE"
        val C_PHONE = "PHONE"
        val FULL_C_PHONE = "INDIVIDUAL.PHONE"
        val C_EMAIL = "EMAIL"
        val FULL_C_EMAIL = "INDIVIDUAL.EMAIL"
        val C_AVAILABLE = "AVAILABLE"
        val FULL_C_AVAILABLE = "INDIVIDUAL.AVAILABLE"
        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL (" + "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + "HOUSEHOLD_ID INTEGER NOT NULL," + "INDIVIDUAL_TYPE_ID INTEGER NOT NULL," + "FIRST_NAME TEXT NOT NULL," + "LAST_NAME TEXT NOT NULL," + "BIRTH_DATE INTEGER," + "PHONE TEXT NOT NULL," + "EMAIL TEXT NOT NULL," + "AVAILABLE INTEGER NOT NULL," + "FOREIGN KEY (HOUSEHOLD_ID) REFERENCES HOUSEHOLD (_id)," + "FOREIGN KEY (INDIVIDUAL_TYPE_ID) REFERENCES INDIVIDUAL_TYPE (_id)" + ");" + "" + ""
        val DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL;"
        val ALL_KEYS = arrayOf(C_ID, C_HOUSEHOLD_ID, C_INDIVIDUAL_TYPE, C_FIRST_NAME, C_LAST_NAME, C_BIRTH_DATE, C_PHONE, C_EMAIL, C_AVAILABLE)

        fun getId(cursor: Cursor) = cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
        fun getHouseholdId(cursor: Cursor) =  cursor.getLong(cursor.getColumnIndexOrThrow(C_HOUSEHOLD_ID))
        fun getIndividualType(cursor: Cursor) =  IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE))]
        fun getFirstName(cursor: Cursor) = cursor.getString(cursor.getColumnIndexOrThrow(C_FIRST_NAME))
        fun getLastName(cursor: Cursor) = cursor.getString(cursor.getColumnIndexOrThrow(C_LAST_NAME))
        fun getBirthDate(cursor: Cursor): org.joda.time.DateTime? {
            return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) org.joda.time.DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) else null
        }
        fun getPhone(cursor: Cursor) = cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE))
        fun getEmail(cursor: Cursor) =  cursor.getString(cursor.getColumnIndexOrThrow(C_EMAIL))
        fun isAvailable(cursor: Cursor) =  if (cursor.getInt(cursor.getColumnIndexOrThrow(C_AVAILABLE)) != 0) true else false
    }
}
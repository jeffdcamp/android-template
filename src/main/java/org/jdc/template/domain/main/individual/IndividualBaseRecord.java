/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individual;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.jdc.template.domain.main.individualtype.IndividualType;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class IndividualBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private long householdId = 0;
    private IndividualType individualType = IndividualType.HEAD;
    private String firstName = "";
    private String lastName = "";
    private org.joda.time.DateTime birthDate = null;
    private org.joda.time.DateTime lastModified = null;
    private String phone = "";
    private String email = "";
    private boolean available = false;

    public IndividualBaseRecord() {
    }

    @Override
    public String getIdColumnName() {
        return IndividualConst.C_ID;
    }

    @Override
    public long getPrimaryKeyId() {
        return id;
    }

    @Override
    public void setPrimaryKeyId(long id) {
        this.id = id;
    }

    @Override
    public String[] getAllColumns() {
        return IndividualConst.ALL_COLUMNS.clone();
    }

    public String[] getAllColumnsFull() {
        return IndividualConst.ALL_COLUMNS_FULL.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(IndividualConst.C_HOUSEHOLD_ID, householdId);
        values.put(IndividualConst.C_INDIVIDUAL_TYPE, individualType.ordinal());
        values.put(IndividualConst.C_FIRST_NAME, firstName);
        values.put(IndividualConst.C_LAST_NAME, lastName);
        values.put(IndividualConst.C_BIRTH_DATE, birthDate != null ? birthDate.getMillis() : null);
        values.put(IndividualConst.C_LAST_MODIFIED, lastModified != null ? lastModified.getMillis() : null);
        values.put(IndividualConst.C_PHONE, phone);
        values.put(IndividualConst.C_EMAIL, email);
        values.put(IndividualConst.C_AVAILABLE, available ? 1 : 0);
        return values;
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            householdId,
            individualType.ordinal(),
            firstName,
            lastName,
            birthDate != null ? birthDate.getMillis() : null,
            lastModified != null ? lastModified.getMillis() : null,
            phone,
            email,
            available ? 1 : 0,
        };
        return values;
    }

    public void setContent(ContentValues values) {
        householdId = values.getAsLong(IndividualConst.C_HOUSEHOLD_ID);
        individualType = IndividualType.values()[values.getAsInteger(IndividualConst.C_INDIVIDUAL_TYPE)];
        firstName = values.getAsString(IndividualConst.C_FIRST_NAME);
        lastName = values.getAsString(IndividualConst.C_LAST_NAME);
        birthDate = dbStringToDateTime(values.getAsString(IndividualConst.C_BIRTH_DATE));
        lastModified = longToDateTime(values.getAsLong(IndividualConst.C_LAST_MODIFIED));
        phone = values.getAsString(IndividualConst.C_PHONE);
        email = values.getAsString(IndividualConst.C_EMAIL);
        available = values.getAsBoolean(IndividualConst.C_AVAILABLE);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_ID));
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_HOUSEHOLD_ID));
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_INDIVIDUAL_TYPE))];
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_NAME));
        birthDate = org.dbtools.android.domain.AndroidBaseRecord.dbStringToDateTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_BIRTH_DATE)));
        lastModified = !cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED)) ? org.dbtools.android.domain.AndroidBaseRecord.longToDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED))) : null;
        phone = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_PHONE));
        email = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_EMAIL));
        available = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_AVAILABLE)) != 0 ? true : false;
    }

    public boolean isNewRecord() {
        return getPrimaryKeyId() <= 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(long householdId) {
        this.householdId = householdId;
    }

    @javax.annotation.Nonnull
    public IndividualType getIndividualType() {
        return individualType;
    }

    public void setIndividualType(@javax.annotation.Nonnull IndividualType individualType) {
        this.individualType = individualType;
    }

    @javax.annotation.Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@javax.annotation.Nonnull String firstName) {
        this.firstName = firstName;
    }

    @javax.annotation.Nonnull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@javax.annotation.Nonnull String lastName) {
        this.lastName = lastName;
    }

    @javax.annotation.Nullable
    public org.joda.time.DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@javax.annotation.Nullable org.joda.time.DateTime birthDate) {
        this.birthDate = birthDate;
    }

    @javax.annotation.Nonnull
    public org.joda.time.DateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(@javax.annotation.Nonnull org.joda.time.DateTime lastModified) {
        this.lastModified = lastModified;
    }

    @javax.annotation.Nonnull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@javax.annotation.Nonnull String phone) {
        this.phone = phone;
    }

    @javax.annotation.Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(@javax.annotation.Nonnull String email) {
        this.email = email;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}
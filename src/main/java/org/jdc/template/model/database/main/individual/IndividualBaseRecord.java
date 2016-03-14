/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individual;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.jdc.template.model.database.main.individualtype.IndividualType;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class IndividualBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private long householdId = 0;
    private IndividualType individualType = IndividualType.HEAD;
    private String firstName = "";
    private String lastName = "";
    private org.threeten.bp.LocalDate birthDate = null;
    private org.threeten.bp.LocalTime alarmTime = null;
    private org.threeten.bp.LocalDateTime lastModified = null;
    private org.threeten.bp.LocalDateTime sampleDateTime = null;
    private org.threeten.bp.LocalDateTime sampleTimestamp = null;
    private String phone = "";
    private String email = "";
    private boolean available = false;
    private Long spouseIndividualId = null;

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
        values.put(IndividualConst.C_BIRTH_DATE, org.dbtools.android.domain.DBToolsDateFormatter.localDateToDBString(birthDate));
        values.put(IndividualConst.C_ALARM_TIME, org.dbtools.android.domain.DBToolsDateFormatter.localTimeToDBString(alarmTime));
        values.put(IndividualConst.C_LAST_MODIFIED, org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(lastModified));
        values.put(IndividualConst.C_SAMPLE_DATE_TIME, org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToDBString(sampleDateTime));
        values.put(IndividualConst.C_SAMPLE_TIMESTAMP, org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(sampleTimestamp));
        values.put(IndividualConst.C_PHONE, phone);
        values.put(IndividualConst.C_EMAIL, email);
        values.put(IndividualConst.C_AVAILABLE, available ? 1 : 0);
        values.put(IndividualConst.C_SPOUSE_INDIVIDUAL_ID, spouseIndividualId);
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
            org.dbtools.android.domain.DBToolsDateFormatter.localDateToDBString(birthDate),
            org.dbtools.android.domain.DBToolsDateFormatter.localTimeToDBString(alarmTime),
            org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(lastModified),
            org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToDBString(sampleDateTime),
            org.dbtools.android.domain.DBToolsDateFormatter.localDateTimeToLong(sampleTimestamp),
            phone,
            email,
            available ? 1 : 0,
            spouseIndividualId,
        };
        return values;
    }

    public void setContent(ContentValues values) {
        householdId = values.getAsLong(IndividualConst.C_HOUSEHOLD_ID);
        individualType = IndividualType.values()[values.getAsInteger(IndividualConst.C_INDIVIDUAL_TYPE)];
        firstName = values.getAsString(IndividualConst.C_FIRST_NAME);
        lastName = values.getAsString(IndividualConst.C_LAST_NAME);
        birthDate = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDate(values.getAsString(IndividualConst.C_BIRTH_DATE));
        alarmTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalTime(values.getAsString(IndividualConst.C_ALARM_TIME));
        lastModified = org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_LAST_MODIFIED));
        sampleDateTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDateTime(values.getAsString(IndividualConst.C_SAMPLE_DATE_TIME));
        sampleTimestamp = org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_SAMPLE_TIMESTAMP));
        phone = values.getAsString(IndividualConst.C_PHONE);
        email = values.getAsString(IndividualConst.C_EMAIL);
        available = values.getAsBoolean(IndividualConst.C_AVAILABLE);
        spouseIndividualId = values.getAsLong(IndividualConst.C_SPOUSE_INDIVIDUAL_ID);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_ID));
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_HOUSEHOLD_ID));
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_INDIVIDUAL_TYPE))];
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_NAME));
        birthDate = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDate(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_BIRTH_DATE)));
        alarmTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_ALARM_TIME)));
        lastModified = !cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED)) ? org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED))) : null;
        sampleDateTime = org.dbtools.android.domain.DBToolsDateFormatter.dbStringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_DATE_TIME)));
        sampleTimestamp = !cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP)) ? org.dbtools.android.domain.DBToolsDateFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP))) : null;
        phone = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_PHONE));
        email = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_EMAIL));
        available = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_AVAILABLE)) != 0 ? true : false;
        spouseIndividualId = !cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SPOUSE_INDIVIDUAL_ID)) ? cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SPOUSE_INDIVIDUAL_ID)) : null;
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
    public org.threeten.bp.LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@javax.annotation.Nullable org.threeten.bp.LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @javax.annotation.Nonnull
    public org.threeten.bp.LocalTime getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(@javax.annotation.Nonnull org.threeten.bp.LocalTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    @javax.annotation.Nonnull
    public org.threeten.bp.LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(@javax.annotation.Nonnull org.threeten.bp.LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @javax.annotation.Nullable
    public org.threeten.bp.LocalDateTime getSampleDateTime() {
        return sampleDateTime;
    }

    public void setSampleDateTime(@javax.annotation.Nullable org.threeten.bp.LocalDateTime sampleDateTime) {
        this.sampleDateTime = sampleDateTime;
    }

    @javax.annotation.Nullable
    public org.threeten.bp.LocalDateTime getSampleTimestamp() {
        return sampleTimestamp;
    }

    public void setSampleTimestamp(@javax.annotation.Nullable org.threeten.bp.LocalDateTime sampleTimestamp) {
        this.sampleTimestamp = sampleTimestamp;
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

    @javax.annotation.Nullable
    public Long getSpouseIndividualId() {
        return spouseIndividualId;
    }

    public void setSpouseIndividualId(@javax.annotation.Nullable Long spouseIndividualId) {
        this.spouseIndividualId = spouseIndividualId;
    }


}
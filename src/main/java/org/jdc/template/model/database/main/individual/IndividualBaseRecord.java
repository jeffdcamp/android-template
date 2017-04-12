/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individual;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.dbtools.android.domain.database.statement.StatementWrapper;
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import android.database.Cursor;


@SuppressWarnings("all")
public abstract class IndividualBaseRecord extends AndroidBaseRecord {

    private long id = 0;
    private long householdId = 0;
    private org.jdc.template.model.type.IndividualType individualType = org.jdc.template.model.type.IndividualType.HEAD;
    private org.jdc.template.model.type.IndividualType individualTypeText = org.jdc.template.model.type.IndividualType.HEAD;
    private String firstName = "";
    private String lastName = "";
    private org.threeten.bp.LocalDate birthDate = null;
    private org.threeten.bp.LocalTime alarmTime = org.threeten.bp.LocalTime.now();
    private org.threeten.bp.LocalDateTime lastModified = org.threeten.bp.LocalDateTime.now();
    private org.threeten.bp.LocalDateTime sampleDateTime = null;
    private org.threeten.bp.LocalDateTime sampleTimestamp = null;
    private String phone = "";
    private String email = "";
    private boolean available = false;
    private float amount1 = 0.0f;
    private double amount2 = 0.0;
    private boolean enabled = false;
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
    public void getContentValues(DBToolsContentValues values) {
        values.put(IndividualConst.C_HOUSEHOLD_ID, householdId);
        values.put(IndividualConst.C_INDIVIDUAL_TYPE, individualType.ordinal());
        values.put(IndividualConst.C_INDIVIDUAL_TYPE_TEXT, individualTypeText.toString());
        values.put(IndividualConst.C_FIRST_NAME, firstName);
        values.put(IndividualConst.C_LAST_NAME, lastName);
        values.put(IndividualConst.C_BIRTH_DATE, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate));
        values.put(IndividualConst.C_ALARM_TIME, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime));
        values.put(IndividualConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified));
        values.put(IndividualConst.C_SAMPLE_DATE_TIME, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime));
        values.put(IndividualConst.C_SAMPLE_TIMESTAMP, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp));
        values.put(IndividualConst.C_PHONE, phone);
        values.put(IndividualConst.C_EMAIL, email);
        values.put(IndividualConst.C_AVAILABLE, available ? 1 : 0);
        values.put(IndividualConst.C_AMOUNT1, amount1);
        values.put(IndividualConst.C_AMOUNT2, amount2);
        values.put(IndividualConst.C_ENABLED, enabled ? 1 : 0);
        values.put(IndividualConst.C_SPOUSE_INDIVIDUAL_ID, spouseIndividualId);
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[]{
            id,
            householdId,
            individualType.ordinal(),
            individualTypeText.toString(),
            firstName,
            lastName,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp),
            phone,
            email,
            available ? 1 : 0,
            amount1,
            amount2,
            enabled ? 1 : 0,
            spouseIndividualId,
        };
        return values;
    }

    public Individual copy() {
        Individual copy = new Individual();
        copy.setId(id);
        copy.setHouseholdId(householdId);
        copy.setIndividualType(individualType);
        copy.setIndividualTypeText(individualTypeText);
        copy.setFirstName(firstName);
        copy.setLastName(lastName);
        copy.setBirthDate(birthDate);
        copy.setAlarmTime(alarmTime);
        copy.setLastModified(lastModified);
        copy.setSampleDateTime(sampleDateTime);
        copy.setSampleTimestamp(sampleTimestamp);
        copy.setPhone(phone);
        copy.setEmail(email);
        copy.setAvailable(available);
        copy.setAmount1(amount1);
        copy.setAmount2(amount2);
        copy.setEnabled(enabled);
        copy.setSpouseIndividualId(spouseIndividualId);
        return copy;
    }

    @Override
    public void bindInsertStatement(StatementWrapper statement) {
        statement.bindLong(1, householdId);
        statement.bindLong(2, individualType.ordinal());
        statement.bindString(3, individualTypeText.toString());
        statement.bindString(4, firstName);
        statement.bindString(5, lastName);
        if (birthDate != null) {
            statement.bindString(6, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate));
        } else {
            statement.bindNull(6);
        }
        statement.bindString(7, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime));
        statement.bindLong(8, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified));
        if (sampleDateTime != null) {
            statement.bindString(9, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime));
        } else {
            statement.bindNull(9);
        }
        if (sampleTimestamp != null) {
            statement.bindLong(10, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp));
        } else {
            statement.bindNull(10);
        }
        statement.bindString(11, phone);
        statement.bindString(12, email);
        statement.bindLong(13, available ? 1 : 0);
        statement.bindDouble(14, amount1);
        statement.bindDouble(15, amount2);
        statement.bindLong(16, enabled ? 1 : 0);
        if (spouseIndividualId != null) {
            statement.bindLong(17, spouseIndividualId);
        } else {
            statement.bindNull(17);
        }
    }

    @Override
    public void bindUpdateStatement(StatementWrapper statement) {
        statement.bindLong(1, householdId);
        statement.bindLong(2, individualType.ordinal());
        statement.bindString(3, individualTypeText.toString());
        statement.bindString(4, firstName);
        statement.bindString(5, lastName);
        if (birthDate != null) {
            statement.bindString(6, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateToDBString(birthDate));
        } else {
            statement.bindNull(6);
        }
        statement.bindString(7, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localTimeToDBString(alarmTime));
        statement.bindLong(8, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified));
        if (sampleDateTime != null) {
            statement.bindString(9, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToDBString(sampleDateTime));
        } else {
            statement.bindNull(9);
        }
        if (sampleTimestamp != null) {
            statement.bindLong(10, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(sampleTimestamp));
        } else {
            statement.bindNull(10);
        }
        statement.bindString(11, phone);
        statement.bindString(12, email);
        statement.bindLong(13, available ? 1 : 0);
        statement.bindDouble(14, amount1);
        statement.bindDouble(15, amount2);
        statement.bindLong(16, enabled ? 1 : 0);
        if (spouseIndividualId != null) {
            statement.bindLong(17, spouseIndividualId);
        } else {
            statement.bindNull(17);
        }
        statement.bindLong(18, id);
    }

    public void setContent(DBToolsContentValues values) {
        householdId = values.getAsLong(IndividualConst.C_HOUSEHOLD_ID);
        individualType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.IndividualType.class, values.getAsInteger(IndividualConst.C_INDIVIDUAL_TYPE), org.jdc.template.model.type.IndividualType.HEAD);
        individualTypeText = org.dbtools.android.domain.util.EnumUtil.stringToEnum(org.jdc.template.model.type.IndividualType.class, values.getAsString(IndividualConst.C_INDIVIDUAL_TYPE_TEXT), org.jdc.template.model.type.IndividualType.HEAD);
        firstName = values.getAsString(IndividualConst.C_FIRST_NAME);
        lastName = values.getAsString(IndividualConst.C_LAST_NAME);
        birthDate = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDate(values.getAsString(IndividualConst.C_BIRTH_DATE));
        alarmTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalTime(values.getAsString(IndividualConst.C_ALARM_TIME));
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_LAST_MODIFIED));
        sampleDateTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDateTime(values.getAsString(IndividualConst.C_SAMPLE_DATE_TIME));
        sampleTimestamp = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(IndividualConst.C_SAMPLE_TIMESTAMP));
        phone = values.getAsString(IndividualConst.C_PHONE);
        email = values.getAsString(IndividualConst.C_EMAIL);
        available = values.getAsBoolean(IndividualConst.C_AVAILABLE);
        amount1 = values.getAsFloat(IndividualConst.C_AMOUNT1);
        amount2 = values.getAsDouble(IndividualConst.C_AMOUNT2);
        enabled = values.getAsBoolean(IndividualConst.C_ENABLED);
        spouseIndividualId = values.getAsLong(IndividualConst.C_SPOUSE_INDIVIDUAL_ID);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_ID));
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_HOUSEHOLD_ID));
        individualType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.IndividualType.class, cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_INDIVIDUAL_TYPE)), org.jdc.template.model.type.IndividualType.HEAD);
        individualTypeText = org.dbtools.android.domain.util.EnumUtil.stringToEnum(org.jdc.template.model.type.IndividualType.class, cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_INDIVIDUAL_TYPE_TEXT)), org.jdc.template.model.type.IndividualType.HEAD);
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_NAME));
        birthDate = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDate(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_BIRTH_DATE)));
        alarmTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_ALARM_TIME)));
        lastModified = !cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED)) ? org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_LAST_MODIFIED))) : null;
        sampleDateTime = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.dbStringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_DATE_TIME)));
        sampleTimestamp = !cursor.isNull(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP)) ? org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(IndividualConst.C_SAMPLE_TIMESTAMP))) : null;
        phone = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_PHONE));
        email = cursor.getString(cursor.getColumnIndexOrThrow(IndividualConst.C_EMAIL));
        available = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_AVAILABLE)) != 0 ? true : false;
        amount1 = cursor.getFloat(cursor.getColumnIndexOrThrow(IndividualConst.C_AMOUNT1));
        amount2 = cursor.getDouble(cursor.getColumnIndexOrThrow(IndividualConst.C_AMOUNT2));
        enabled = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualConst.C_ENABLED)) != 0 ? true : false;
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
    public org.jdc.template.model.type.IndividualType getIndividualType() {
        return individualType;
    }

    public void setIndividualType(@javax.annotation.Nonnull org.jdc.template.model.type.IndividualType individualType) {
        this.individualType = individualType;
    }

    @javax.annotation.Nonnull
    public org.jdc.template.model.type.IndividualType getIndividualTypeText() {
        return individualTypeText;
    }

    public void setIndividualTypeText(@javax.annotation.Nonnull org.jdc.template.model.type.IndividualType individualTypeText) {
        this.individualTypeText = individualTypeText;
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

    public float getAmount1() {
        return amount1;
    }

    public void setAmount1(float amount1) {
        this.amount1 = amount1;
    }

    public double getAmount2() {
        return amount2;
    }

    public void setAmount2(double amount2) {
        this.amount2 = amount2;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @javax.annotation.Nullable
    public Long getSpouseIndividualId() {
        return spouseIndividualId;
    }

    public void setSpouseIndividualId(@javax.annotation.Nullable Long spouseIndividualId) {
        this.spouseIndividualId = spouseIndividualId;
    }


}
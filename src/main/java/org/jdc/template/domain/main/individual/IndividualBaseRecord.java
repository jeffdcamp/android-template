/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individual;

import org.dbtools.android.domain.AndroidBaseRecord;
import android.database.Cursor;
import org.jdc.template.domain.main.individualtype.IndividualType;
import android.content.ContentValues;


@SuppressWarnings("all")
public abstract class IndividualBaseRecord extends AndroidBaseRecord {

    public static final String DATABASE = "main";
    public static final String TABLE = "INDIVIDUAL";
    public static final String FULL_TABLE = "main.INDIVIDUAL";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL._id";
    private long id = 0;
    public static final String C_HOUSEHOLD_ID = "HOUSEHOLD_ID";
    public static final String FULL_C_HOUSEHOLD_ID = "INDIVIDUAL.HOUSEHOLD_ID";
    private long householdId = 0;
    public static final String C_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE_ID";
    public static final String FULL_C_INDIVIDUAL_TYPE = "INDIVIDUAL.INDIVIDUAL_TYPE_ID";
    private IndividualType individualType = IndividualType.HEAD;
    public static final String C_FIRST_NAME = "FIRST_NAME";
    public static final String FULL_C_FIRST_NAME = "INDIVIDUAL.FIRST_NAME";
    private String firstName = "";
    public static final String C_LAST_NAME = "LAST_NAME";
    public static final String FULL_C_LAST_NAME = "INDIVIDUAL.LAST_NAME";
    private String lastName = "";
    public static final String C_BIRTH_DATE = "BIRTH_DATE";
    public static final String FULL_C_BIRTH_DATE = "INDIVIDUAL.BIRTH_DATE";
    private org.joda.time.DateTime birthDate = null;
    public static final String C_PHONE = "PHONE";
    public static final String FULL_C_PHONE = "INDIVIDUAL.PHONE";
    private String phone = "";
    public static final String C_EMAIL = "EMAIL";
    public static final String FULL_C_EMAIL = "INDIVIDUAL.EMAIL";
    private String email = "";
    public static final String C_AVAILABLE = "AVAILABLE";
    public static final String FULL_C_AVAILABLE = "INDIVIDUAL.AVAILABLE";
    private boolean available = false;
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
    public static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_HOUSEHOLD_ID,
        C_INDIVIDUAL_TYPE,
        C_FIRST_NAME,
        C_LAST_NAME,
        C_BIRTH_DATE,
        C_PHONE,
        C_EMAIL,
        C_AVAILABLE};

    public IndividualBaseRecord() {
    }

    @Override
    public String getIdColumnName() {
        return C_ID;
    }

    @Override
    public long getPrimaryKeyId() {
        return id;
    }

    @Override
    public void setPrimaryKeyId(long id) {
        this.id = id;
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
        return !cursor.isNull(cursor.getColumnIndexOrThrow(C_BIRTH_DATE)) ? new org.joda.time.DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) : null;
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

    @Override
    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(C_HOUSEHOLD_ID, householdId);
        values.put(C_INDIVIDUAL_TYPE, individualType.ordinal());
        values.put(C_FIRST_NAME, firstName);
        values.put(C_LAST_NAME, lastName);
        values.put(C_BIRTH_DATE, birthDate != null ? birthDate.getMillis() : null);
        values.put(C_PHONE, phone);
        values.put(C_EMAIL, email);
        values.put(C_AVAILABLE, available ? 1 : 0);
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
            phone,
            email,
            available ? 1 : 0,
        };
        return values;
    }

    public void setContent(ContentValues values) {
        householdId = values.getAsLong(C_HOUSEHOLD_ID);
        individualType = IndividualType.values()[values.getAsInteger(C_INDIVIDUAL_TYPE)];
        firstName = values.getAsString(C_FIRST_NAME);
        lastName = values.getAsString(C_LAST_NAME);
        birthDate = new org.joda.time.DateTime(values.getAsLong(C_BIRTH_DATE));
        phone = values.getAsString(C_PHONE);
        email = values.getAsString(C_EMAIL);
        available = values.getAsBoolean(C_AVAILABLE);
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
        householdId = cursor.getLong(cursor.getColumnIndexOrThrow(C_HOUSEHOLD_ID));
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(C_INDIVIDUAL_TYPE))];
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(C_FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow(C_LAST_NAME));
        birthDate = !cursor.isNull(cursor.getColumnIndexOrThrow(C_BIRTH_DATE)) ? new org.joda.time.DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_BIRTH_DATE))) : null;
        phone = cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE));
        email = cursor.getString(cursor.getColumnIndexOrThrow(C_EMAIL));
        available = cursor.getInt(cursor.getColumnIndexOrThrow(C_AVAILABLE)) != 0 ? true : false;
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
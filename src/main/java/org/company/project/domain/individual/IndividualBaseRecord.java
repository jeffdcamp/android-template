/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individual;

import org.dbtools.android.domain.AndroidBaseRecord;
import org.company.project.domain.individualtype.IndividualType;
import android.content.ContentValues;
import android.database.Cursor;


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
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "HOUSEHOLD_ID INTEGER NOT NULL," + 
        "INDIVIDUAL_TYPE_ID INTEGER NOT NULL," + 
        "FIRST_NAME TEXT NOT NULL," + 
        "LAST_NAME TEXT NOT NULL," + 
        "BIRTH_DATE INTEGER," + 
        "PHONE TEXT," + 
        "EMAIL TEXT," + 
        "FOREIGN KEY (HOUSEHOLD_ID) REFERENCES HOUSEHOLD (_id)," + 
        "FOREIGN KEY (INDIVIDUAL_TYPE_ID) REFERENCES INDIVIDUAL_TYPE (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL;";
     static final String[] ALL_KEYS = new String[] {
        C_ID,
        C_HOUSEHOLD_ID,
        C_INDIVIDUAL_TYPE,
        C_FIRST_NAME,
        C_LAST_NAME,
        C_BIRTH_DATE,
        C_PHONE,
        C_EMAIL};

    public IndividualBaseRecord() {
    }

    @Override
    public String getDatabaseName() {
        return DATABASE;
    }

    @Override
    public String getTableName() {
        return TABLE;
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
    }

    @Override
    public void setContent(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(C_ID));
        householdId = cursor.getLong(cursor.getColumnIndex(C_HOUSEHOLD_ID));
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndex(C_INDIVIDUAL_TYPE))];
        firstName = cursor.getString(cursor.getColumnIndex(C_FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndex(C_LAST_NAME));
        birthDate = !cursor.isNull(cursor.getColumnIndex(C_BIRTH_DATE)) ? new org.joda.time.DateTime(cursor.getLong(cursor.getColumnIndex(C_BIRTH_DATE))) : null;
        phone = cursor.getString(cursor.getColumnIndex(C_PHONE));
        email = cursor.getString(cursor.getColumnIndex(C_EMAIL));
    }

    @Override
    public String toString() {
        String text = "\n";
        text += "id = "+ id +"\n";
        text += "householdId = "+ householdId +"\n";
        text += "individualType = "+ individualType +"\n";
        text += "firstName = "+ firstName +"\n";
        text += "lastName = "+ lastName +"\n";
        text += "birthDate = "+ birthDate +"\n";
        text += "phone = "+ phone +"\n";
        text += "email = "+ email +"\n";
        return text;
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

    public IndividualType getIndividualType() {
        return individualType;
    }

    public void setIndividualType(IndividualType individualType) {
        this.individualType = individualType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public org.joda.time.DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(org.joda.time.DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
/*
 * IndividualBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.company.project.domain.individual;

import org.company.project.domain.BaseRecord;
import android.provider.BaseColumns;
import org.company.project.domain.individualtype.IndividualType;
import android.content.ContentValues;
import android.database.Cursor;


@SuppressWarnings("PMD")
public class IndividualBaseRecord extends BaseRecord {

    public static final String DATABASE = "main";
    public static final String TABLE = "INDIVIDUAL";
    public static final String FULL_TABLE = "main.INDIVIDUAL";
    public static final String KEY_ID = BaseColumns._ID;
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL._id";
    private long Id = 0;
    public static final String KEY_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE_ID";
    public static final String C_INDIVIDUAL_TYPE = "INDIVIDUAL_TYPE_ID";
    public static final String FULL_C_INDIVIDUAL_TYPE = "INDIVIDUAL.INDIVIDUAL_TYPE_ID";
    private IndividualType individualType = IndividualType.HEAD;
    public static final String KEY_NAME = "NAME";
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "INDIVIDUAL.NAME";
    private String name = "";
    public static final String KEY_BIRTH_DATE = "BIRTH_DATE";
    public static final String C_BIRTH_DATE = "BIRTH_DATE";
    public static final String FULL_C_BIRTH_DATE = "INDIVIDUAL.BIRTH_DATE";
    private org.joda.time.DateTime birthDate = null;
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.sss";
    public static final org.joda.time.format.DateTimeFormatter DB_DATE_FORMATTER = org.joda.time.format.DateTimeFormat.forPattern(DB_DATE_FORMAT);
    public static final String KEY_PHONE = "PHONE";
    public static final String C_PHONE = "PHONE";
    public static final String FULL_C_PHONE = "INDIVIDUAL.PHONE";
    private String phone = "";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String C_EMAIL = "EMAIL";
    public static final String FULL_C_EMAIL = "INDIVIDUAL.EMAIL";
    private String email = "";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "INDIVIDUAL_TYPE_ID INTEGER NOT NULL," + 
        "NAME TEXT NOT NULL," + 
        "BIRTH_DATE TEXT," + 
        "PHONE TEXT," + 
        "EMAIL TEXT," + 
        "FOREIGN KEY (INDIVIDUAL_TYPE_ID) REFERENCES INDIVIDUAL_TYPE (_id)" + 
        ");" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL;";
     static final String[] ALL_KEYS = new String[] {
        KEY_ID,
        KEY_INDIVIDUAL_TYPE,
        KEY_NAME,
        KEY_BIRTH_DATE,
        KEY_PHONE,
        KEY_EMAIL};

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
    public String getRowIDKey() {
        return KEY_ID;
    }

    @Override
    public long getID() {
        return Id;
    }

    @Override
    public void setID(long id) {
        Id = id;
    }

    public String dateTimeToDBString(org.joda.time.DateTime d) {
        if (d != null) {
            return d.toString(DB_DATE_FORMAT);
        } else {
            return null;
        }
    }

    public org.joda.time.DateTime dbStringToDateTime(String text) {
        if (text != null && text.length() > 0 && !text.equals("null")) {
            try {
                return DB_DATE_FORMATTER.parseDateTime(text);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot parse date text: " + text, ex);
            }
        } else {
            return null;
        }
    }

    @Override
    public String[] getAllKeys() {
        return ALL_KEYS.clone();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_INDIVIDUAL_TYPE, individualType.ordinal());
        values.put(KEY_NAME, name);
        values.put(KEY_BIRTH_DATE, dateTimeToDBString(birthDate));
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        return values;
    }

    public void setContent(ContentValues values) {
        individualType = IndividualType.values()[values.getAsInteger(KEY_INDIVIDUAL_TYPE)];
        name = values.getAsString(KEY_NAME);
        birthDate = dbStringToDateTime(values.getAsString(KEY_BIRTH_DATE));
        phone = values.getAsString(KEY_PHONE);
        email = values.getAsString(KEY_EMAIL);
    }

    @Override
    public void setContent(Cursor cursor) {
        Id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        individualType = IndividualType.values()[cursor.getInt(cursor.getColumnIndex(KEY_INDIVIDUAL_TYPE))];
        name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
        birthDate = dbStringToDateTime(cursor.getString(cursor.getColumnIndex(KEY_BIRTH_DATE)));
        phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
        email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
    }

    protected void cleanupOrphans() {
    }

    @Override
    public String toString() {
        String text = "\n";
        text += "Id = "+ Id +"\n";
        text += "individualType = "+ individualType +"\n";
        text += "name = "+ name +"\n";
        text += "birthDate = "+ birthDate +"\n";
        text += "phone = "+ phone +"\n";
        text += "email = "+ email +"\n";
        return text;
    }

    public boolean isNewRecord() {
        return getID() <= 0;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public IndividualType getIndividualType() {
        return individualType;
    }

    public void setIndividualType(IndividualType individualType) {
        this.individualType = individualType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
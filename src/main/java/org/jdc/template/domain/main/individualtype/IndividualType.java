/*
 * IndividualType.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individualtype;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import android.database.Cursor;


@SuppressWarnings("all")
public enum IndividualType {
HEAD, SPOUSE, CHILD;

    private static Map<IndividualType, String> enumStringMap = new EnumMap<IndividualType, String>(IndividualType.class);
    private static List<String> stringList = new ArrayList<String>();
    public static final String DATABASE = "main";
    public static final String TABLE = "INDIVIDUAL_TYPE";
    public static final String FULL_TABLE = "main.INDIVIDUAL_TYPE";
    public static final String PRIMARY_KEY_COLUMN = "_id";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "INDIVIDUAL_TYPE._id";
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "INDIVIDUAL_TYPE.NAME";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS INDIVIDUAL_TYPE (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "NAME TEXT NOT NULL," + 
        "UNIQUE(NAME)" + 
        ");" + 
        "" + 
        "" + 
        "INSERT INTO INDIVIDUAL_TYPE (_id, NAME) VALUES (0, 'Head');" + 
        "INSERT INTO INDIVIDUAL_TYPE (_id, NAME) VALUES (1, 'Spouse');" + 
        "INSERT INTO INDIVIDUAL_TYPE (_id, NAME) VALUES (2, 'Child');" + 
        "" + 
        "";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS INDIVIDUAL_TYPE;";

    static {
        enumStringMap.put(HEAD, "Head");
        stringList.add("Head");
        
        enumStringMap.put(SPOUSE, "Spouse");
        stringList.add("Spouse");
        
        enumStringMap.put(CHILD, "Child");
        stringList.add("Child");
        

    }

    public static String getString(IndividualType key) {
        return enumStringMap.get(key);
    }

    public static List<String> getList() {
        return Collections.unmodifiableList(stringList);
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
    }


}
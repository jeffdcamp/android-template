/*
 * CrossDatabaseQueryBaseRecord.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.attached.crossdatabasequery;

import android.database.Cursor;


@SuppressWarnings("all")
public class CrossDatabaseQueryConst {

    public static final String DATABASE = "attached";
    public static final String C_ID = "_id";
    public static final String FULL_C_ID = "CROSS_DATABASE_QUERY._id";
    public static final String C_NAME = "NAME";
    public static final String FULL_C_NAME = "CROSS_DATABASE_QUERY.NAME";
    public static final String C_TYPE = "TYPE";
    public static final String FULL_C_TYPE = "CROSS_DATABASE_QUERY.TYPE";
    public static final String[] ALL_COLUMNS = new String[] {
        C_ID,
        C_NAME,
        C_TYPE};
    public static final String[] ALL_COLUMNS_FULL = new String[] {
        FULL_C_ID,
        FULL_C_NAME,
        FULL_C_TYPE};

    public CrossDatabaseQueryConst() {
    }

    public static long getId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID));
    }

    public static String getName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME));
    }

    public static org.jdc.template.model.type.LocationType getType(Cursor cursor) {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.LocationType.class, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.jdc.template.model.type.LocationType.HOME);
    }


}
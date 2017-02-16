/*
 * CrossDatabaseQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.attached.crossdatabasequery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class CrossDatabaseQueryBaseRecord  : AndroidBaseRecord {

     open var id: Long = 0
     open var name: String = ""
     open var type: org.jdc.template.model.type.LocationType = org.jdc.template.model.type.LocationType.HOME

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return CrossDatabaseQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return CrossDatabaseQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(CrossDatabaseQueryConst.C_ID, id)
        values.put(CrossDatabaseQueryConst.C_NAME, name)
        values.put(CrossDatabaseQueryConst.C_TYPE, type.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            name,
            type.ordinal.toLong())
    }

    open fun copy() : CrossDatabaseQuery {
        val copy = CrossDatabaseQuery()
        copy.id = id
        copy.name = name
        copy.type = type
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, name)
        statement.bindLong(3, type.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, name)
        statement.bindLong(3, type.ordinal.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(CrossDatabaseQueryConst.C_ID)
        name = values.getAsString(CrossDatabaseQueryConst.C_NAME)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.LocationType::class.java, values.getAsInteger(CrossDatabaseQueryConst.C_TYPE), org.jdc.template.model.type.LocationType.HOME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_NAME))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.jdc.template.model.type.LocationType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(CrossDatabaseQueryConst.C_TYPE)), org.jdc.template.model.type.LocationType.HOME)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
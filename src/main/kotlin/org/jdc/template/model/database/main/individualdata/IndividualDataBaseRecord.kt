/*
 * IndividualDataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.main.individualdata

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class IndividualDataBaseRecord : AndroidBaseRecord {

     open var externalId: Long = 0
     open var typeId: Int = 0
     open var name: String = ""

    constructor(record: IndividualData) {
        this.externalId = record.externalId
        this.typeId = record.typeId
        this.name = record.name
    }

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return IndividualDataConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return IndividualDataConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(IndividualDataConst.C_EXTERNAL_ID, externalId)
        values.put(IndividualDataConst.C_TYPE_ID, (typeId as Int).toLong())
        values.put(IndividualDataConst.C_NAME, name)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            externalId,
            (typeId as Int).toLong(),
            name)
    }

    open fun copy() : IndividualData {
        val copy = IndividualData()
        copy.externalId = externalId
        copy.typeId = typeId
        copy.name = name
        return copy
    }

    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, externalId)
        statement.bindLong(2, (typeId as Int).toLong())
        statement.bindString(3, name)
    }

    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, externalId)
        statement.bindLong(2, (typeId as Int).toLong())
        statement.bindString(3, name)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        externalId = values.getAsLong(IndividualDataConst.C_EXTERNAL_ID)
        typeId = values.getAsInteger(IndividualDataConst.C_TYPE_ID)
        name = values.getAsString(IndividualDataConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        externalId = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualDataConst.C_EXTERNAL_ID))
        typeId = cursor.getInt(cursor.getColumnIndexOrThrow(IndividualDataConst.C_TYPE_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(IndividualDataConst.C_NAME))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }

    override fun getIdColumnName() : String {
        return "NO_PRIMARY_KEY"
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
        // NO_PRIMARY_KEY
    }


}
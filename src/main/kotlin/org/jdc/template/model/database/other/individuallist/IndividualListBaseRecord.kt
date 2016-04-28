/*
 * IndividualListBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.model.database.other.individuallist

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@SuppressWarnings("all")
abstract class IndividualListBaseRecord : AndroidBaseRecord() {

     var id: Long = 0
     var name: String = ""

    override fun getIdColumnName(): String {
        return IndividualListConst.C_ID
    }

    override fun getPrimaryKeyId(): Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns(): Array<String> {
        return IndividualListConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull(): Array<String> {
        return IndividualListConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(IndividualListConst.C_NAME, name)
    }

    override fun getValues(): Array<Any?> {
        return arrayOf(
            id,
            name)
    }

    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString( 1, name)
    }

    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString( 1, name)
        statement.bindLong( 2, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(IndividualListConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(IndividualListConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(IndividualListConst.C_NAME))
    }

    override fun isNewRecord(): Boolean {
        return primaryKeyId <= 0
    }


}
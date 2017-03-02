/*
 * IndividualDataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individualdata

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.dbtools.android.domain.AndroidBaseRecord


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class IndividualDataBaseManager  : RxKotlinAndroidBaseManagerWritable<IndividualData> {

     override val allColumns: Array<String> = IndividualDataConst.ALL_COLUMNS
     override val primaryKey = "NO_PRIMARY_KEY"
     override val dropSql = IndividualDataConst.DROP_TABLE
     override val createSql = IndividualDataConst.CREATE_TABLE
     override val insertSql = IndividualDataConst.INSERT_STATEMENT
     override val updateSql = IndividualDataConst.UPDATE_STATEMENT
     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return IndividualDataConst.DATABASE
    }

    override fun newRecord() : IndividualData {
        return IndividualData()
    }

    override fun getTableName() : String {
        return IndividualDataConst.TABLE
    }

    override fun getReadableDatabase(@javax.annotation.Nonnull databaseName: String) : DatabaseWrapper<in AndroidBaseRecord, in DBToolsContentValues<*>> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    override fun getWritableDatabase(@javax.annotation.Nonnull databaseName: String) : DatabaseWrapper<in AndroidBaseRecord, in DBToolsContentValues<*>> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    override fun getAndroidDatabase(@javax.annotation.Nonnull databaseName: String) : org.dbtools.android.domain.AndroidDatabase? {
        return databaseManager.getDatabase(databaseName)
    }

    override fun getDatabaseConfig() : org.dbtools.android.domain.config.DatabaseConfig {
        return databaseManager.databaseConfig
    }


}
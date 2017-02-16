/*
 * IndividualBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.main.individual

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.jdc.template.model.database.DatabaseManager


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class IndividualBaseManager  : RxKotlinAndroidBaseManagerWritable<Individual> {

     override val tableName = IndividualConst.TABLE
     override val allColumns: Array<String> = IndividualConst.ALL_COLUMNS
     override val primaryKey = IndividualConst.PRIMARY_KEY_COLUMN
     override val dropSql = IndividualConst.DROP_TABLE
     override val createSql = IndividualConst.CREATE_TABLE
     override val insertSql = IndividualConst.INSERT_STATEMENT
     override val updateSql = IndividualConst.UPDATE_STATEMENT
     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return IndividualConst.DATABASE
    }

    override fun newRecord() : Individual {
        return Individual()
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
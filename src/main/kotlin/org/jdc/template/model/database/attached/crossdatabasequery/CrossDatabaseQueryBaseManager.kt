/*
 * CrossDatabaseQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.model.database.attached.crossdatabasequery

import org.jdc.template.model.database.DatabaseManager
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.dbtools.android.domain.AndroidBaseRecord


@Suppress("unused", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class CrossDatabaseQueryBaseManager  : RxKotlinAndroidBaseManagerReadOnly<CrossDatabaseQuery> {

     override val allColumns: Array<String> = CrossDatabaseQueryConst.ALL_COLUMNS
     override val tableName = getQuery()
     override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
     override val dropSql = ""
     override val createSql = ""
     override val insertSql = ""
     override val updateSql = ""
     var databaseManager: DatabaseManager

    constructor(databaseManager: DatabaseManager) {
        this.databaseManager = databaseManager
    }

    override fun getDatabaseName() : String {
        return CrossDatabaseQueryConst.DATABASE
    }

    override fun newRecord() : CrossDatabaseQuery {
        return CrossDatabaseQuery()
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

    abstract fun getQuery() : String


}
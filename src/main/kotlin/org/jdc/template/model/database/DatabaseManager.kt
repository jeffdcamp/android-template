
package org.jdc.template.model.database

import android.util.Log
import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.DatabaseConfig
import javax.inject.Singleton


@Singleton
class DatabaseManager : DatabaseBaseManager {

    companion object {
        val mainTablesVersion = 1
        val mainViewsVersion = 1
        val otherTablesVersion = 1
        val otherViewsVersion = 1
        val attachedTablesVersion = 1
        val attachedViewsVersion = 1
    }

    @javax.inject.Inject
    constructor(databaseConfig: DatabaseConfig) : super(databaseConfig) {
    }

    override fun onUpgrade(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        Log.i(TAG, "Upgrading database [$androidDatabase.name] from version $oldVersion to $newVersion")
    }

    override fun onUpgradeViews(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        Log.i(TAG, "Upgrading database [$androidDatabase.name] VIEWS from version $oldVersion to $newVersion")
        // automatically drop/create views
        super.onUpgradeViews(androidDatabase, oldVersion, newVersion)
    }

    fun initDatabaseConnection() {
        Log.i(TAG, "Initializing Database connection: ")
        try {
            getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open database... attempting to recreate database", e)
            cleanAllDatabases()
            getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME)
        }
    }
}
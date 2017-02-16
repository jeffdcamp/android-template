
package org.jdc.template.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.DatabaseConfig
import timber.log.Timber
import javax.inject.Singleton


@Singleton
class DatabaseManager : DatabaseBaseManager {

    companion object {
        const val mainTablesVersion = 1
        const val mainViewsVersion = 1
        const val otherTablesVersion = 1
        const val otherViewsVersion = 1
        const val attachedTablesVersion = 1
        const val attachedViewsVersion = 1
    }

    @javax.inject.Inject
    constructor(databaseConfig: DatabaseConfig) : super(databaseConfig) {
    }

    override fun onUpgrade(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        Timber.i("Upgrading database [$androidDatabase.name] from version $oldVersion to $newVersion")
    }

    override fun onUpgradeViews(androidDatabase: AndroidDatabase, oldVersion: Int, newVersion: Int) {
        Timber.i("Upgrading database [$androidDatabase.name] VIEWS from version $oldVersion to $newVersion")
        // automatically drop/create views
        super.onUpgradeViews(androidDatabase, oldVersion, newVersion)
    }

    fun initDatabaseConnection() {
        Timber.i("Initializing Database connection: ")
        try {
            getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME)
        } catch (e: Exception) {
            Timber.e(e, "Failed to open database... attempting to recreate database")
            cleanAllDatabases()
            getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME)
        }
    }
}

package org.jdc.template.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.DatabaseConfig
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DatabaseManager@Inject constructor(databaseConfig: DatabaseConfig)  : DatabaseBaseManager(databaseConfig) {

    companion object {
         const val MAIN_TABLES_VERSION = 1
         const val MAIN_VIEWS_VERSION = 1
         const val OTHER_TABLES_VERSION = 1
         const val OTHER_VIEWS_VERSION = 1
         const val ATTACHED_TABLES_VERSION = 1
         const val ATTACHED_VIEWS_VERSION = 1
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
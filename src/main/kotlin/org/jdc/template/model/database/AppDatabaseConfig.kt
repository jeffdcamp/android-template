
package org.jdc.template.model.database

import android.app.Application
import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.AndroidDatabaseBaseManager
import org.dbtools.android.domain.config.DatabaseConfig
import org.dbtools.android.domain.database.AndroidDatabaseWrapper
import org.dbtools.android.domain.database.DatabaseWrapper
import org.dbtools.android.domain.database.contentvalues.AndroidDBToolsContentValues
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import org.dbtools.android.domain.log.DBToolsAndroidLogger
import org.dbtools.android.domain.log.DBToolsLogger


class AppDatabaseConfig : DatabaseConfig {

    val application: Application

    constructor(application: Application) {
        this.application = application
    }

    override fun identifyDatabases(databaseManager: AndroidDatabaseBaseManager) {
        databaseManager.addDatabase(application, DatabaseManagerConst.MAIN_DATABASE_NAME, DatabaseManager.mainTablesVersion, DatabaseManager.mainViewsVersion)
        databaseManager.addDatabase(application, DatabaseManagerConst.OTHER_DATABASE_NAME, DatabaseManager.otherTablesVersion, DatabaseManager.otherViewsVersion)
        databaseManager.addDatabase(application, DatabaseManagerConst.ATTACHED_DATABASE_NAME, DatabaseManager.attachedTablesVersion, DatabaseManager.attachedViewsVersion)
    }

    override fun createNewDatabaseWrapper(androidDatabase: AndroidDatabase): DatabaseWrapper<*, *> {
        return AndroidDatabaseWrapper(androidDatabase.path)
    }

    override fun createNewDBToolsContentValues(): DBToolsContentValues<*> {
        return AndroidDBToolsContentValues()
    }

    override fun createNewDBToolsLogger(): DBToolsLogger {
        return DBToolsAndroidLogger()
    }


}
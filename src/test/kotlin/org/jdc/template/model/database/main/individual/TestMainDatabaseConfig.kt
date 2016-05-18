package org.jdc.template.model.database.main.individual

import org.dbtools.android.domain.config.BuildEnv
import org.dbtools.android.domain.config.TestDatabaseConfig
import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.model.database.DatabaseManagerConst

class TestMainDatabaseConfig(databaseFilename: String) :
        TestDatabaseConfig(DatabaseManagerConst.MAIN_DATABASE_NAME, BuildEnv.GRADLE, databaseFilename, DatabaseManager.mainTablesVersion, DatabaseManager.mainViewsVersion)

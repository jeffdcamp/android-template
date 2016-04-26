package org.jdc.template.model.database.main.individual

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.TestDatabaseConfig
import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.model.database.DatabaseManagerConst
import java.util.*

class TestMainDatabaseConfig(databaseName: String) :
        TestDatabaseConfig(
                databaseName,
                Arrays.asList(AndroidDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME,
                                TestDatabaseConfig.TEST_DB_DIR + databaseName,
                                DatabaseManager.mainTablesVersion, DatabaseManager.mainViewsVersion)))

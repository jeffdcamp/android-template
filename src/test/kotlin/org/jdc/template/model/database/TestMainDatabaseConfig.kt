package org.jdc.template.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.BuildEnv
import org.dbtools.android.domain.config.TestDatabaseConfig
import java.util.*

class TestMainDatabaseConfig(androidDatabaseList: List<AndroidDatabase>) : TestDatabaseConfig(androidDatabaseList) {
    companion object {
        val instance: TestMainDatabaseConfig
            get() {
                val databases = ArrayList<AndroidDatabase>()
                databases.add(AndroidDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME, BuildEnv.GRADLE.testDbDir + DatabaseManagerConst.MAIN_DATABASE_NAME, DatabaseManager.mainTablesVersion, DatabaseManager.mainViewsVersion))

                return TestMainDatabaseConfig(databases)
            }
    }
}

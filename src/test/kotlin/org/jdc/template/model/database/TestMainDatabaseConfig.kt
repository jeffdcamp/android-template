package org.jdc.template.model.database

import org.dbtools.android.domain.AndroidDatabase
import org.dbtools.android.domain.config.TestDatabaseConfig

class TestMainDatabaseConfig(androidDatabaseList: List<AndroidDatabase>) : TestDatabaseConfig(androidDatabaseList) {
//    companion object {
//        val instance: TestMainDatabaseConfig
//            get() {
//                val databases = ArrayList<AndroidDatabase>()
//                databases.add(AndroidDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME, BuildEnv.GRADLE.testDbDir + DatabaseManagerConst.MAIN_DATABASE_NAME, DatabaseManager.MAIN_TABLES_VERSION, DatabaseManager.MAIN_VIEWS_VERSION))
//
//                return TestMainDatabaseConfig(databases)
//            }
//    }
}

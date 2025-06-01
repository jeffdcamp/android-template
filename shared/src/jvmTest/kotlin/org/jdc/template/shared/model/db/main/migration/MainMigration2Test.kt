package org.jdc.template.shared.model.db.main.migration

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import assertk.assertThat
import assertk.assertions.isFalse
import okio.FileSystem
import okio.Path.Companion.toPath
import org.dbtools.room.ext.columnExists
import org.dbtools.room.ext.deleteDatabaseFiles
import org.jdc.template.shared.model.db.main.MainDatabase
import kotlin.test.BeforeTest
import kotlin.test.Test

class MainMigration2Test {

    private val databasePath = "build/test/db/${MainDatabase.DATABASE_NAME}".toPath()

    private val mainDatabaseMigrationTestHelper = MigrationTestHelper(
        schemaDirectoryPath = "schemas".toPath().toNioPath(),
        driver = BundledSQLiteDriver(),
        databaseClass = MainDatabase::class,
        databasePath = databasePath.toNioPath()
    )

    @BeforeTest
    fun beforeTest() {
        FileSystem.SYSTEM.deleteDatabaseFiles(databasePath)
    }

    @Test
    fun emptyMigrationTest() {
        // Create the database at version 1
        mainDatabaseMigrationTestHelper.createDatabase(version = 1).close()

        // Migrate the database to version 2
        val migratedConnection: SQLiteConnection = mainDatabaseMigrationTestHelper.runMigrationsAndValidate(
            version = 2,
            migrations = listOf(MainMigration2)
        )
        migratedConnection.close()
    }

    @Test
    fun migrationTest() {
        // Create the database at version 1
        val v1Connection = mainDatabaseMigrationTestHelper.createDatabase(version = 1)
        // no data needs to be inserted for this test
        v1Connection.close()

        // Migrate the database to version 2
        val v2Connection: SQLiteConnection = mainDatabaseMigrationTestHelper.runMigrationsAndValidate(
            version = 2,
            migrations = listOf(MainMigration2)
        )

        assertThat(v2Connection.columnExists("Individual", "extra")).isFalse()
        v2Connection.close()
    }
}

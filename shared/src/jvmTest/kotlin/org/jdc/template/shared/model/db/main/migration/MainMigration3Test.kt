package org.jdc.template.shared.model.db.main.migration

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import okio.FileSystem
import okio.Path.Companion.toPath
import org.dbtools.room.ext.deleteDatabaseFiles
import org.dbtools.room.ext.getColumnIndexOrThrow
import org.jdc.template.shared.model.db.main.MainDatabase
import kotlin.test.BeforeTest
import kotlin.test.Test

class MainMigration3Test {

    private val databasePath = "build/test/db/${MainDatabase.DATABASE_NAME}".toPath()

    private val mainDatabaseMigrationTestHelper = MigrationTestHelper(
        schemaDirectoryPath = "schemas".toPath().toNioPath(),
        driver = BundledSQLiteDriver(),// sqliteDriver,
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
        val newConnection = mainDatabaseMigrationTestHelper.createDatabase(version = 2)
        newConnection.close()
    }

    @Test
    fun migrationTest() {
        // Create the database at version 2
        val v2Connection = mainDatabaseMigrationTestHelper.createDatabase(version = 2)

        // Insert some data that should be preserved
        v2Connection.execSQL(
            "INSERT INTO Individual (id, firstName, individualType, availabley, created, lastModified) VALUES ('1', 'Jeff', 'HEAD', 1, '2024-01-01T12:00:00Z', '2024-01-01T12:00:00Z')"
        )
        v2Connection.close()

        // Migrate the database to version 3
        val v3Connection = mainDatabaseMigrationTestHelper.runMigrationsAndValidate(
            version = 3,
            listOf(org.jdc.template.shared.model.db.main.MainDatabase_AutoMigration_2_3_Impl())
        )
        v3Connection.prepare("SELECT available FROM Individual WHERE id = '1'").use { statement ->
            // Validates data is preserved between migrations.
            assertThat(statement.step()).isTrue()
            assertThat(statement.getText(statement.getColumnIndexOrThrow("available"))).isEqualTo("1")
        }
        v3Connection.close()
    }
}

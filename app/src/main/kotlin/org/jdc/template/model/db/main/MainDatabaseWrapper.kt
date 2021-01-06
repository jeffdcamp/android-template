package org.jdc.template.model.db.main

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import org.dbtools.android.room.CloseableDatabaseWrapper
import org.dbtools.android.room.ext.createAllViews
import org.dbtools.android.room.ext.dropAllViews
import org.dbtools.android.room.ext.recreateAllViews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MainDatabaseWrapper
@Inject constructor(
    @ApplicationContext context: Context
) : CloseableDatabaseWrapper<MainDatabase>(context) {

    override fun createDatabase(): MainDatabase {
        return Room.databaseBuilder(context, MainDatabase::class.java, MainDatabase.DATABASE_NAME)
            .addMigrations(object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // ONLY views are changed

                    // drop and recreate views
                    database.recreateAllViews(MainDatabase.DATABASE_VIEW_QUERIES)
                }
            })
            .addMigrations(object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // BOTH views and tables are changed

                    // drop views
                    database.dropAllViews()

                    // do other database migrations here

                    // recreate views
                    database.createAllViews(MainDatabase.DATABASE_VIEW_QUERIES)
                }
            })
            // Debug -- Show SQL statements
            //.setQueryCallback({ sql, args -> Timber.d("${MainDatabase.DATABASE_NAME} Query: [$sql]  Args: $args") }) { it.run() }
            .build()
    }
}
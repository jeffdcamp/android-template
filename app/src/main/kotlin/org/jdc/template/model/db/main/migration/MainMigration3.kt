package org.jdc.template.model.db.main.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.dbtools.android.room.ext.createAllViews
import org.dbtools.android.room.ext.dropAllViews
import org.jdc.template.model.db.main.MainDatabase

class MainMigration3: Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // BOTH views and tables are changed

        // drop views
        database.dropAllViews()

        // do other database migrations here

        // recreate views
        database.createAllViews(MainDatabase.DATABASE_VIEW_QUERIES)
    }
}
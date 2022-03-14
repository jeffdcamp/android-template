package org.jdc.template.model.db.main.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.dbtools.android.room.ext.recreateAllViews
import org.jdc.template.model.db.main.MainDatabase

class MainMigration2: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // ONLY views are changed

        // drop and recreate views
        database.recreateAllViews(MainDatabase.DATABASE_VIEW_QUERIES)
    }
}
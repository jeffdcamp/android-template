package org.jdc.template.shared.model.db.main.migration

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import org.dbtools.room.ext.recreateAllViews
import org.jdc.template.shared.model.db.main.MainDatabase

class MainMigration2: Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        // ONLY views are changed

        // drop and recreate views
        connection.recreateAllViews(MainDatabase.DATABASE_VIEW_QUERIES)
    }
}
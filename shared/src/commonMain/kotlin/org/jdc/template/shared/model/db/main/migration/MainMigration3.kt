package org.jdc.template.shared.model.db.main.migration

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import org.dbtools.room.ext.createAllViews
import org.dbtools.room.ext.dropAllViews
import org.jdc.template.shared.model.db.main.MainDatabase

class MainMigration3: Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        // BOTH views and tables are changed

        // drop views
        connection.dropAllViews()

        // do other database migrations here

        // recreate views
        connection.createAllViews(MainDatabase.DATABASE_VIEW_QUERIES)
    }
}
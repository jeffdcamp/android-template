package org.jdc.template.shared.model.db.main.migration

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

@RenameColumn(tableName = "Individual", fromColumnName = "availabley", toColumnName = "available")
class MainAutoMigrationSpec3 : AutoMigrationSpec

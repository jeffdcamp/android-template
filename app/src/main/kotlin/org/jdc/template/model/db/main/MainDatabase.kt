package org.jdc.template.model.db.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.dbtools.android.room.DatabaseViewQuery
import org.jdc.template.model.db.converter.DateTimeTextConverter
import org.jdc.template.model.db.main.directoryitem.DirectoryItemDao
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.db.main.household.HouseholdDao
import org.jdc.template.model.db.main.household.HouseholdEntity
import org.jdc.template.model.db.main.individual.IndividualDao
import org.jdc.template.model.db.main.individual.IndividualEntity

@Database(
    entities = [
        IndividualEntity::class,
        HouseholdEntity::class
    ],
    views = [
        DirectoryItemEntityView::class
    ],
    version = 1
)
@TypeConverters(DateTimeTextConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao
    abstract fun directoryItemDao(): DirectoryItemDao

    companion object {
        const val DATABASE_NAME = "main.db"
        val DATABASE_VIEW_QUERIES = listOf(
            DatabaseViewQuery(DirectoryItemEntityView.VIEW_NAME, DirectoryItemEntityView.VIEW_QUERY)
        )
    }
}
package org.jdc.template.model.db.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.dbtools.android.room.DatabaseViewQuery
import org.jdc.template.model.db.converter.DateTimeTextConverter
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.db.main.directoryitem.DirectoryItemDao
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.household.HouseholdDao
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.individual.IndividualDao

@Database(
    entities = [
        Individual::class,
        Household::class
    ],
    views = [
        DirectoryItem::class
    ],
    version = 1
)
@TypeConverters(DateTimeTextConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract val individualDao: IndividualDao
    abstract val householdDao: HouseholdDao
    abstract val directoryItemDao: DirectoryItemDao

    companion object {
        const val DATABASE_NAME = "main.db"
        val DATABASE_VIEW_QUERIES = listOf(
            DatabaseViewQuery(DirectoryItem.VIEW_NAME, DirectoryItem.VIEW_QUERY)
        )
    }
}
package org.jdc.template.datasource.database.main

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import org.jdc.template.datasource.database.converter.DateTextConverters
import org.jdc.template.datasource.database.converter.DateTimeTextConverter
import org.jdc.template.datasource.database.main.converter.MainDatabaseConverters
import org.jdc.template.datasource.database.main.household.Household
import org.jdc.template.datasource.database.main.household.HouseholdDao
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao

@Database(entities = arrayOf(
        Individual::class,
        Household::class),
        version = 1)
@TypeConverters(MainDatabaseConverters::class, DateTimeTextConverter::class, DateTextConverters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao

    companion object {
        const val DATABASE_NAME = "main"
    }
}
package org.jdc.template.model.db.main

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import org.jdc.template.model.db.converter.DateTextConverters
import org.jdc.template.model.db.converter.DateTimeTextConverter
import org.jdc.template.model.db.main.converter.MainDatabaseConverters
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.household.HouseholdDao
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.individual.IndividualDao

@Database(entities = [Individual::class, Household::class], version = 1)
@TypeConverters(MainDatabaseConverters::class, DateTimeTextConverter::class, DateTextConverters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao

    companion object {
        const val DATABASE_NAME = "main"
    }
}
package org.jdc.template.shared.model.db.main

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.dbtools.room.DatabaseViewQuery
import org.jdc.template.shared.model.db.converter.DataValueClassTypeConverters
import org.jdc.template.shared.model.db.converter.KotlinDateTimeTextConverter
import org.jdc.template.shared.model.db.main.chatmessage.ChatMessageDao
import org.jdc.template.shared.model.db.main.chatmessage.ChatMessageEntity
import org.jdc.template.shared.model.db.main.chatthread.ChatThreadDao
import org.jdc.template.shared.model.db.main.chatthread.ChatThreadEntity
import org.jdc.template.shared.model.db.main.directoryitem.DirectoryItemDao
import org.jdc.template.shared.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.shared.model.db.main.household.HouseholdDao
import org.jdc.template.shared.model.db.main.household.HouseholdEntity
import org.jdc.template.shared.model.db.main.individual.IndividualDao
import org.jdc.template.shared.model.db.main.individual.IndividualEntity
import org.jdc.template.shared.model.db.main.migration.MainAutoMigrationSpec3
import org.jdc.template.shared.model.db.main.migration.MainMigration2

@Database(
    entities = [
        IndividualEntity::class,
        HouseholdEntity::class,
        ChatThreadEntity::class,
        ChatMessageEntity::class
    ],
    views = [
        DirectoryItemEntityView::class
    ],
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = MainAutoMigrationSpec3::class)
    ],
    version = 3
)
@ConstructedBy(MainDatabaseConstructor::class)
@TypeConverters(KotlinDateTimeTextConverter::class, DataValueClassTypeConverters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao
    abstract fun directoryItemDao(): DirectoryItemDao
    abstract fun chatThreadDao(): ChatThreadDao
    abstract fun chatMessageDao(): ChatMessageDao

    companion object {
        const val DATABASE_NAME = "main.db"

        fun getDatabase(builder: Builder<MainDatabase>): MainDatabase {
            return builder
                .addMigrations(
                    MainMigration2,
                )
                .setDriver(BundledSQLiteDriver())
                .build()
        }

        val DATABASE_VIEW_QUERIES = listOf(
            DatabaseViewQuery(DirectoryItemEntityView.VIEW_NAME, DirectoryItemEntityView.VIEW_QUERY)
        )
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object MainDatabaseConstructor : RoomDatabaseConstructor<MainDatabase> {
    override fun initialize(): MainDatabase
}

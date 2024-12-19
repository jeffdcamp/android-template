package org.jdc.template.model.db.main

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.dbtools.android.room.DatabaseViewQuery
import org.jdc.template.model.db.converter.KotlinDateTimeTextConverter
import org.jdc.template.model.db.main.chatmessage.ChatMessageDao
import org.jdc.template.model.db.main.chatmessage.ChatMessageEntity
import org.jdc.template.model.db.main.chatthread.ChatThreadDao
import org.jdc.template.model.db.main.chatthread.ChatThreadEntity
import org.jdc.template.model.db.main.directoryitem.DirectoryItemDao
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.db.main.household.HouseholdDao
import org.jdc.template.model.db.main.household.HouseholdEntity
import org.jdc.template.model.db.main.individual.IndividualDao
import org.jdc.template.model.db.main.individual.IndividualEntity

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
        AutoMigration(from = 3, to = 4)
    ],
    version = 4
)
@TypeConverters(KotlinDateTimeTextConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao
    abstract fun directoryItemDao(): DirectoryItemDao
    abstract fun chatThreadDao(): ChatThreadDao
    abstract fun chatMessageDao(): ChatMessageDao

    companion object {
        const val DATABASE_NAME = "main.db"
        val DATABASE_VIEW_QUERIES = listOf(
            DatabaseViewQuery(DirectoryItemEntityView.VIEW_NAME, DirectoryItemEntityView.VIEW_QUERY)
        )
    }
}
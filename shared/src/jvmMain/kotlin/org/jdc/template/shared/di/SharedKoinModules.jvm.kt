package org.jdc.template.shared.di

import androidx.room.Room
import androidx.room.RoomDatabase
import org.jdc.template.shared.model.db.main.MainDatabase
import org.jdc.template.shared.util.file.AppFileSystem
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseBuilderModule: Module = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        Room.databaseBuilder<MainDatabase>(
            name = AppFileSystem.getDatabasesPath(MainDatabase.DATABASE_NAME).toFile().absolutePath,
        )
    }
}

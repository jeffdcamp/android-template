package org.jdc.template.shared.di

import androidx.room.Room
import androidx.room.RoomDatabase
import org.jdc.template.shared.model.db.main.MainDatabase
import org.jdc.template.shared.util.file.AppFileSystem
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val databaseBuilderModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        Room.databaseBuilder<MainDatabase>(
            context = androidApplication(),
            name = AppFileSystem.getDatabasesPath(MainDatabase.DATABASE_NAME).toFile().absolutePath,
        )
    }
}

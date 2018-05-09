package org.jdc.template.datasource.database.main.converter

import android.arch.persistence.room.TypeConverter
import org.jdc.template.datasource.database.main.type.IndividualType

class MainDatabaseConverters {
    @TypeConverter
    fun fromStringToType(value: String): IndividualType {
        return IndividualType.valueOf(value)
    }

    @TypeConverter
    fun fromTypeToString(value: IndividualType): String {
        return value.toString()
    }
}
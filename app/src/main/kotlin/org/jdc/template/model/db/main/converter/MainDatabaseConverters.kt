package org.jdc.template.model.db.main.converter

import androidx.room.TypeConverter
import org.jdc.template.model.db.main.type.IndividualType
import org.jdc.template.util.enumValueOfOrDefault

class MainDatabaseConverters {
    @TypeConverter
    fun fromStringToType(value: String) = enumValueOfOrDefault(value, IndividualType.HEAD)

    @TypeConverter
    fun fromTypeToString(value: IndividualType) = value.toString()
}
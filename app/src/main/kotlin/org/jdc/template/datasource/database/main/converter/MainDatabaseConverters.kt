package org.jdc.template.datasource.database.main.converter

import android.arch.persistence.room.TypeConverter
import org.jdc.template.datasource.database.converter.ConverterUtil
import org.jdc.template.datasource.database.main.type.IndividualType

class MainDatabaseConverters {
    @TypeConverter
    fun fromStringToType(value: String) = try { IndividualType.valueOf(value.toUpperCase()) } catch (e: Exception) { ConverterUtil.getDefaultAndLogInvalidEnum(e, IndividualType.HEAD) }

    @TypeConverter
    fun fromTypeToString(value: IndividualType) = value.toString()
}
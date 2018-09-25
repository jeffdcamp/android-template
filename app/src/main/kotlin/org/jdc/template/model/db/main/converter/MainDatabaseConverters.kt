package org.jdc.template.model.db.main.converter

import androidx.room.TypeConverter
import org.jdc.template.model.db.converter.ConverterUtil
import org.jdc.template.model.db.main.type.IndividualType

class MainDatabaseConverters {
    @TypeConverter
    fun fromStringToType(value: String) = try { IndividualType.valueOf(value.toUpperCase()) } catch (e: Exception) { ConverterUtil.getDefaultAndLogInvalidEnum(e, IndividualType.HEAD) }

    @TypeConverter
    fun fromTypeToString(value: IndividualType) = value.toString()
}
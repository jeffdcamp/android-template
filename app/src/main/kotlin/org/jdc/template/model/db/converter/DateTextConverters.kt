package org.jdc.template.model.db.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class DateTextConverters {
    @TypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? {
        return ThreeTenFormatter.dbStringToLocalDate(value)
    }

    @TypeConverter
    fun fromLocalDateToString(value: LocalDate?): String? {
        return ThreeTenFormatter.localDateToDBString(value)
    }

    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? {
        return ThreeTenFormatter.dbStringToLocalTime(value)
    }

    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? {
        return ThreeTenFormatter.localTimeToDBString(value)
    }
}
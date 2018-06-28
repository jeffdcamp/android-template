package org.jdc.template.model.db.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class DateTimeTextConverter {
    @TypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? {
        return ThreeTenFormatter.dbStringToLocalDateTime(value)
    }

    @TypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime?): String? {
        return ThreeTenFormatter.localDateTimeToDBString(value)
    }

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
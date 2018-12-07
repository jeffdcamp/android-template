package org.jdc.template.model.db.converter

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime

class DateTimeTextConverter {
    @TypeConverter
    fun fromStringToOffsetDateTime(value: String?): OffsetDateTime? {
        return ThreeTenFormatter.dbStringToOffsetDateTime(value)
    }

    @TypeConverter
    fun fromOffsetDateTimeToString(value: OffsetDateTime?): String? {
        return ThreeTenFormatter.offsetDateTimeToDBString(value)
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
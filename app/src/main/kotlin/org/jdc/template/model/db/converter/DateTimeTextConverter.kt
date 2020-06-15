package org.jdc.template.model.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

class DateTimeTextConverter {
    @TypeConverter
    fun fromStringToOffsetDateTime(value: String?): OffsetDateTime? = ThreeTenFormatter.dbStringToOffsetDateTime(value)

    @TypeConverter
    fun fromOffsetDateTimeToString(value: OffsetDateTime?): String? = ThreeTenFormatter.offsetDateTimeToDBString(value)

    @TypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? = ThreeTenFormatter.dbStringToLocalDate(value)

    @TypeConverter
    fun fromLocalDateToString(value: LocalDate?): String? = ThreeTenFormatter.localDateToDBString(value)

    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? = ThreeTenFormatter.dbStringToLocalTime(value)

    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? = ThreeTenFormatter.localTimeToDBString(value)
}
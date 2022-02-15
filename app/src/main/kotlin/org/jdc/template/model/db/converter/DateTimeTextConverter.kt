package org.jdc.template.model.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

object DateTimeTextConverter {
    @TypeConverter
    fun fromStringToOffsetDateTimeNullable(value: String?): OffsetDateTime? = ThreeTenFormatter.dbStringToOffsetDateTimeNullable(value)
    @TypeConverter
    fun fromOffsetDateTimeToStringNullable(value: OffsetDateTime?): String? = ThreeTenFormatter.offsetDateTimeToDBStringNullable(value)
    @TypeConverter
    fun fromStringToOffsetDateTimeNotNull(value: String): OffsetDateTime = ThreeTenFormatter.dbStringToOffsetDateTimeNotNull(value)
    @TypeConverter
    fun fromOffsetDateTimeToStringNotNull(value: OffsetDateTime): String = ThreeTenFormatter.offsetDateTimeToDBStringNotNull(value)

    @TypeConverter
    fun fromStringToLocalDateNullable(value: String?): LocalDate? = ThreeTenFormatter.dbStringToLocalDateNullable(value)
    @TypeConverter
    fun fromLocalDateToStringNullable(value: LocalDate?): String? = ThreeTenFormatter.localDateToDBStringNullable(value)
    @TypeConverter
    fun fromStringToLocalDateNotNull(value: String): LocalDate = ThreeTenFormatter.dbStringToLocalDateNotNull(value)
    @TypeConverter
    fun fromLocalDateToStringNotNull(value: LocalDate): String = ThreeTenFormatter.localDateToDBStringNotNull(value)

    @TypeConverter
    fun fromStringToLocalTimeNullable(value: String?): LocalTime? = ThreeTenFormatter.dbStringToLocalTimeNullable(value)
    @TypeConverter
    fun fromLocalTimeToStringNullable(value: LocalTime?): String? = ThreeTenFormatter.localTimeToDBStringNullable(value)
    @TypeConverter
    fun fromStringToLocalTimeNotNull(value: String): LocalTime = ThreeTenFormatter.dbStringToLocalTimeNotNull(value)
    @TypeConverter
    fun fromLocalTimeToStringNotNull(value: LocalTime): String = ThreeTenFormatter.localTimeToDBStringNotNull(value)
}
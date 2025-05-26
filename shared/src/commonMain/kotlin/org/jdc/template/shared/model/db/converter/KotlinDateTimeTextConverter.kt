package org.jdc.template.shared.model.db.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

object KotlinDateTimeTextConverter {
    @TypeConverter
    fun fromStringToInstantNullable(value: String?): Instant? = if (!value.isNullOrEmpty() && value != "null") Instant.parse(value) else null
    @TypeConverter
    fun fromInstantToStringNullable(value: Instant?): String? = value?.toString()
    @TypeConverter
    fun fromStringToInstantNotNull(value: String): Instant = Instant.parse(value)
    @TypeConverter
    fun fromInstantToStringNotNull(value: Instant): String = value.toString()

    @TypeConverter
    fun fromStringToLocalDateTimeNullable(value: String?): LocalDateTime? = if (!value.isNullOrEmpty() && value != "null") LocalDateTime.parse(value) else null
    @TypeConverter
    fun fromLocalDateTimeToStringNullable(value: LocalDateTime?): String? = value?.toString()
    @TypeConverter
    fun fromStringToLocalTimeDateNotNull(value: String): LocalDateTime = LocalDateTime.parse(value)
    @TypeConverter
    fun fromLocalDateTimeToStringNotNull(value: LocalDateTime): String = value.toString()

    @TypeConverter
    fun fromStringToLocalDateNullable(value: String?): LocalDate? = if (!value.isNullOrEmpty() && value != "null") LocalDate.parse(value) else null
    @TypeConverter
    fun fromLocalDateToStringNullable(value: LocalDate?): String? = value?.toString()
    @TypeConverter
    fun fromStringToLocalDateNotNull(value: String): LocalDate = LocalDate.parse(value)
    @TypeConverter
    fun fromLocalDateToStringNotNull(value: LocalDate): String = value.toString()

    @TypeConverter
    fun fromStringToLocalTimeNullable(value: String?): LocalTime? = if (!value.isNullOrEmpty() && value != "null") LocalTime.parse(value) else null
    @TypeConverter
    fun fromLocalTimeToStringNullable(value: LocalTime?): String? = value?.toString()
    @TypeConverter
    fun fromStringToLocalTimeNotNull(value: String): LocalTime = LocalTime.parse(value)
    @TypeConverter
    fun fromLocalTimeToStringNotNull(value: LocalTime): String = value.toString()
}
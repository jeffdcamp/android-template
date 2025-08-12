package org.jdc.template.shared.model.db.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

object KotlinDateTimeTextConverter {
    @TypeConverter
    fun fromStringToInstant(value: String?): Instant? = if (value.isNullOrBlank()) null else Instant.parse(value)
    @TypeConverter
    fun fromInstantToString(value: Instant?): String? = value?.toString()

    @TypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? = if (value.isNullOrBlank()) null else LocalDateTime.parse(value)
    @TypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? = if (value.isNullOrBlank()) null else LocalDate.parse(value)
    @TypeConverter
    fun fromLocalDateToString(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? = if (value.isNullOrBlank()) null else LocalTime.parse(value)
    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? = value?.toString()
}
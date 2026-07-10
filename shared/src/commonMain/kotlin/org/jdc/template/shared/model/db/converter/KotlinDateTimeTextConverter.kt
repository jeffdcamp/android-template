package org.jdc.template.shared.model.db.converter

import androidx.room3.ColumnTypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

object KotlinDateTimeTextConverter {
    @ColumnTypeConverter
    fun fromStringToInstant(value: String?): Instant? = if (value.isNullOrBlank()) null else Instant.parse(value)
    @ColumnTypeConverter
    fun fromInstantToString(value: Instant?): String? = value?.toString()

    @ColumnTypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? = if (value.isNullOrBlank()) null else LocalDateTime.parse(value)
    @ColumnTypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime?): String? = value?.toString()

    @ColumnTypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? = if (value.isNullOrBlank()) null else LocalDate.parse(value)
    @ColumnTypeConverter
    fun fromLocalDateToString(value: LocalDate?): String? = value?.toString()

    @ColumnTypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? = if (value.isNullOrBlank()) null else LocalTime.parse(value)
    @ColumnTypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? = value?.toString()
}
package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

class DateTextConverters {
    @TypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? {
        if (value != null && value.isNotEmpty() && value != "null") {
            try {
                return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)
            } catch (var2: Exception) {
                throw IllegalArgumentException("Cannot parse date text: " + value, var2)
            }

        } else {
            return null
        }
    }

    @TypeConverter
    fun fromLocalDateToString(value: LocalDate?): String? {
        value ?: return null
        return DateTimeFormatter.ISO_LOCAL_DATE.format(value)
    }

    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? {
        if (value != null && value.isNotEmpty() && value != "null") {
            try {
                return LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME)
            } catch (var2: Exception) {
                throw IllegalArgumentException("Cannot parse date text: " + value, var2)
            }

        } else {
            return null
        }    }

    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? {
        value ?: return null
        return DateTimeFormatter.ISO_LOCAL_TIME.format(value)
    }
}
package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateTimeTextConverter {
    @TypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? {
        if (value != null && value.isNotEmpty() && value != "null") {
            try {
                return LocalDateTime.parse(value, DB_DATETIME_FORMATTER310)
            } catch (e: Exception) {
                throw IllegalArgumentException("Cannot parse date time text: $value", e)
            }

        } else {
            return null
        }
    }

    @TypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime?): String? {
        return if (value != null) DB_DATETIME_FORMATTER310.format(value) else null
    }

    companion object {
        val DB_DATETIME_FORMATTER310 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    }
}
package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDateTime

class DateTimeLongConverter {
    @TypeConverter
    fun fromLongToLocalDateTime(value: Long?): LocalDateTime? {
        return ThreeTenFormatter.longToLocalDateTime(value)
    }

    @TypeConverter
    fun fromLocalDateTimeToLong(value: LocalDateTime?): Long? {
        return ThreeTenFormatter.localDateTimeToLong(value)
    }
}
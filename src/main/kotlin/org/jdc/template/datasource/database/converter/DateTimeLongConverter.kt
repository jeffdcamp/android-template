package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class DateTimeLongConverter {
    @TypeConverter
    fun fromLongToLocalDateTime(value: Long?): LocalDateTime? {
        value ?: return null
        return Instant.ofEpochMilli(value.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    @TypeConverter
    fun fromLocalDateTimeToLong(value: LocalDateTime?): Long? {
        value ?: return null
        return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
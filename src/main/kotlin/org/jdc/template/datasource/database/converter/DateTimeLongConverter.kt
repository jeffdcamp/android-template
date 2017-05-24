package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.threeten.bp.LocalDateTime

class DateTimeLongConverter {
    @TypeConverter
    fun fromLongToLocalDateTime(value: Long?): LocalDateTime? {
        return DBToolsThreeTenFormatter.longToLocalDateTime(value)
    }

    @TypeConverter
    fun fromLocalDateTimeToLong(value: LocalDateTime?): Long? {
        return DBToolsThreeTenFormatter.localDateTimeToLong(value)
    }
}
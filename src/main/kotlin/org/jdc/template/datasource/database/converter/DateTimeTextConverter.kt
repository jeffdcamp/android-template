package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.threeten.bp.LocalDateTime

class DateTimeTextConverter {
    @TypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? {
        return DBToolsThreeTenFormatter.dbStringToLocalDateTime(value)
    }

    @TypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime?): String? {
        return DBToolsThreeTenFormatter.localDateTimeToDBString(value)
    }
}
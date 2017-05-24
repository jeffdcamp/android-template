package org.jdc.template.datasource.database.converter

import android.arch.persistence.room.TypeConverter
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class DateTextConverters {
    @TypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? {
        return DBToolsThreeTenFormatter.dbStringToLocalDate(value)
    }

    @TypeConverter
    fun fromLocalDateToString(value: LocalDate?): String? {
        return DBToolsThreeTenFormatter.localDateToDBString(value)
    }

    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? {
        return DBToolsThreeTenFormatter.dbStringToLocalTime(value)
    }

    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? {
        return DBToolsThreeTenFormatter.localTimeToDBString(value)
    }
}
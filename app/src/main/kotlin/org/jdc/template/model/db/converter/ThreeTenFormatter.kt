package org.jdc.template.model.db.converter

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

object ThreeTenFormatter {
    // ========== JSR 310 ==========
    // JSR 310 LocalDateTime - long
    fun localDateTimeToLong(d: LocalDateTime?): Long? {
        if (d == null) {
            return null
        }

        return d.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }


    fun longToLocalDateTime(l: Long?): LocalDateTime? {
        if (l == null) {
            return null
        }

        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }


    fun localDateTimeToLongUtc(d: LocalDateTime?): Long? {
        if (d == null) {
            return null
        }

        return d.toInstant(ZoneOffset.UTC).toEpochMilli()
    }


    fun longToLocalDateTimeUtc(l: Long?): LocalDateTime? {
        if (l == null) {
            return null
        }

        return Instant.ofEpochMilli(l).atZone(ZoneOffset.UTC).toLocalDateTime()
    }

    // JSR 310 LocalDateTime - String

    // Date and Time
    fun localDateTimeToDBString(d: LocalDateTime?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(d)
        } else {
            null
        }
    }

    fun dbStringToLocalDateTime(text: String?): LocalDateTime? {
        return if (text != null && text.isNotEmpty() && text != "null") {
            try {
                LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse date time text: $text", ex)
            }

        } else {
            null
        }
    }

    // Date only
    fun localDateToDBString(d: LocalDate?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_LOCAL_DATE.format(d)
        } else {
            null
        }
    }

    fun dbStringToLocalDate(text: String?): LocalDate? {
        return if (text != null && text.isNotEmpty() && text != "null") {
            try {
                LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse date text: $text", ex)
            }

        } else {
            null
        }
    }

    // Time only
    fun localTimeToDBString(d: LocalTime?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_LOCAL_TIME.format(d)
        } else {
            null
        }
    }

    fun dbStringToLocalTime(text: String?): LocalTime? {
        return if (text != null && text.isNotEmpty() && text != "null") {
            try {
                LocalTime.parse(text, DateTimeFormatter.ISO_LOCAL_TIME)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse time text: $text", ex)
            }

        } else {
            null
        }
    }
}

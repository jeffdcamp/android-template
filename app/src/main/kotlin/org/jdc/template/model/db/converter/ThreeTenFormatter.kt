package org.jdc.template.model.db.converter

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object ThreeTenFormatter {
    // OffsetDateTime
    fun offsetDateTimeToDBString(d: OffsetDateTime?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(d)
        } else {
            null
        }
    }

    fun dbStringToOffsetDateTime(text: String?): OffsetDateTime? {
        return if (text != null && text.isNotEmpty() && text != "null") {
            try {
                OffsetDateTime.parse(text)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse date text: $text", ex)
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

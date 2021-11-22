package org.jdc.template.model.db.converter

import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object ThreeTenFormatter {
    // OffsetDateTime
    fun offsetDateTimeToDBStringNullable(d: OffsetDateTime?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_INSTANT.format(d)
        } else {
            null
        }
    }

    fun dbStringToOffsetDateTimeNullable(text: String?): OffsetDateTime? {
        return if (!text.isNullOrEmpty() && text != "null") {
            try {
                OffsetDateTime.parse(text)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse date text: $text", ex)
            }
        } else {
            null
        }
    }

    fun offsetDateTimeToDBStringNotNull(d: OffsetDateTime): String = DateTimeFormatter.ISO_INSTANT.format(d)
    fun dbStringToOffsetDateTimeNotNull(text: String): OffsetDateTime = OffsetDateTime.parse(text)

    // Date only
    fun localDateToDBStringNullable(d: LocalDate?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_LOCAL_DATE.format(d)
        } else {
            null
        }
    }

    fun dbStringToLocalDateNullable(text: String?): LocalDate? {
        return if (!text.isNullOrEmpty() && text != "null") {
            try {
                LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse date text: $text", ex)
            }

        } else {
            null
        }
    }

    fun localDateToDBStringNotNull(d: LocalDate): String = DateTimeFormatter.ISO_LOCAL_DATE.format(d)
    fun dbStringToLocalDateNotNull(text: String): LocalDate = LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE)

    // Time only
    fun localTimeToDBStringNullable(d: LocalTime?): String? {
        return if (d != null) {
            DateTimeFormatter.ISO_LOCAL_TIME.format(d)
        } else {
            null
        }
    }

    fun dbStringToLocalTimeNullable(text: String?): LocalTime? {
        return if (!text.isNullOrEmpty() && text != "null") {
            try {
                LocalTime.parse(text, DateTimeFormatter.ISO_LOCAL_TIME)
            } catch (ex: Exception) {
                throw IllegalArgumentException("Cannot parse time text: $text", ex)
            }

        } else {
            null
        }
    }

    fun localTimeToDBStringNotNull(d: LocalTime): String = DateTimeFormatter.ISO_LOCAL_TIME.format(d)
    fun dbStringToLocalTimeNotNull(text: String): LocalTime = LocalTime.parse(text, DateTimeFormatter.ISO_LOCAL_TIME)
}

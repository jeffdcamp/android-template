package org.jdc.template.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

import java.io.IOException

class DateTimeStringSerializer : JsonSerializer<LocalDateTime>() {

    @Throws(IOException::class)
    override fun serialize(value: LocalDateTime?, jsonGenerator: JsonGenerator, provider: SerializerProvider) {
        if (value != null) {
            jsonGenerator.writeString(FORMATTER.format(value))
        } else {
            jsonGenerator.writeNull()
        }
    }

    companion object {
        val FORMATTER = DateTimeFormatter.ISO_DATE_TIME
    }
}

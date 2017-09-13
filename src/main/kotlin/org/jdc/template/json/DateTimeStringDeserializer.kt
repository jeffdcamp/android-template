package org.jdc.template.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

import java.io.IOException

class DateTimeStringDeserializer : JsonDeserializer<LocalDateTime>() {

    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): LocalDateTime? {
        val dateString = jsonParser.getValueAsString(null)
        return if (dateString != null) {
            LocalDateTime.parse(jsonParser.text, FORMATTER)
        } else {
            null
        }
    }

    companion object {
        val FORMATTER = DateTimeFormatter.ISO_DATE_TIME
    }
}

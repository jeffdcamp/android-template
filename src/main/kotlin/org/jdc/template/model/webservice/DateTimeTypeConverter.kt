package org.jdc.template.model.webservice

import com.google.gson.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class DateTimeTypeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    override fun serialize(src: LocalDateTime, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): LocalDateTime {
        return LocalDateTime.parse(json.asString, FORMATTER)
    }

    companion object {
        val FORMATTER = DateTimeFormatter.ISO_DATE_TIME
    }
}
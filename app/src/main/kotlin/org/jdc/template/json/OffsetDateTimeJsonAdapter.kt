package org.jdc.template.json

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * As agreed with iOS team.... all pushed OffsetDateTime values to PDS would be truncated at millis and in UTC time zone
 */
class OffsetDateTimeJsonAdapter : TypeAdapter<OffsetDateTime>() {
    override fun write(out: JsonWriter, value: OffsetDateTime?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value))
        }
    }

    override fun read(reader: JsonReader): OffsetDateTime? {
        val value = reader.nextString()

        return if (value != null) {
            OffsetDateTime.parse(value)
        } else {
            null
        }
    }
}

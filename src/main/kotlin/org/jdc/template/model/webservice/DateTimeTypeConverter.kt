package org.jdc.template.model.webservice

import com.bluelinelabs.logansquare.typeconverters.TypeConverter
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateTimeTypeConverter : TypeConverter<LocalDateTime> {
    override fun serialize(obj: LocalDateTime?, fieldName: String?, writeFieldNameForObject: Boolean, jsonGenerator: JsonGenerator) {
        if (fieldName != null && obj != null) {
            jsonGenerator.writeStringField(fieldName, DateTimeFormatter.ISO_DATE_TIME.format(obj));
        } else if (obj != null) {
            jsonGenerator.writeString(DateTimeFormatter.ISO_DATE_TIME.format(obj));
        } else {
            if (fieldName != null) {
                jsonGenerator.writeFieldName(fieldName);
            }
            jsonGenerator.writeNull();
        }
    }

    override fun parse(jsonParser: JsonParser): LocalDateTime? {
        val dateString = jsonParser.getValueAsString(null);
        if (dateString != null) {
            return LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_DATE_TIME);
        } else {
            return null;
        }
    }
}
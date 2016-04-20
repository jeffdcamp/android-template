package org.jdc.template.model.webservice;

import com.bluelinelabs.logansquare.typeconverters.TypeConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;

public class DateTimeTypeConverter implements TypeConverter<LocalDateTime> {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void serialize(LocalDateTime object, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException {
        if (fieldName != null && object != null) {
            jsonGenerator.writeStringField(fieldName, FORMATTER.format(object));
        } else if (object != null) {
            jsonGenerator.writeString(FORMATTER.format(object));
        } else {
            if (fieldName != null) {
                jsonGenerator.writeFieldName(fieldName);
            }
            jsonGenerator.writeNull();
        }
    }

    @Override
    public LocalDateTime parse(JsonParser jsonParser) throws IOException {
        String dateString = jsonParser.getValueAsString(null);
        if (dateString != null) {
            return LocalDateTime.parse(jsonParser.getText(), FORMATTER);
        } else {
            return null;
        }
    }
}
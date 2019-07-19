package com.hoang.lsp.core.dataformat.deserializer;

import java.util.Calendar;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

public class UnixTimestampDeserializer extends DateDeserializers.CalendarDeserializer {

    @Override
    public Calendar deserialize (JsonParser jp, DeserializationContext ctxt) {
        try {
            JsonToken curr = jp.getCurrentToken();
            if ( curr == JsonToken.VALUE_STRING ) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.setTimeInMillis(Long.valueOf(jp.getText()) * 1000);
                return calendar;
            }
        }catch (Exception ex){
        }
        return null;
    }
}

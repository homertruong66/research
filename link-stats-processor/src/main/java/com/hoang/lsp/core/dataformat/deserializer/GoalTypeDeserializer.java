package com.hoang.lsp.core.dataformat.deserializer;

import java.io.IOException;

import com.hoang.lsp.core.GoalType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class GoalTypeDeserializer extends JsonDeserializer<GoalType> {

    @Override
    public GoalType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING) {
            return GoalType.parse(jp.getText());
        }
        throw new InvalidFormatException("Invalid token", jp.getText(), GoalType.class);
    }

}

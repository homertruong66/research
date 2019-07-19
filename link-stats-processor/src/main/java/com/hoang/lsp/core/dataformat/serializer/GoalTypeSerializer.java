package com.hoang.lsp.core.dataformat.serializer;

import java.io.IOException;

import com.hoang.lsp.core.GoalType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class GoalTypeSerializer extends JsonSerializer<GoalType> {

    @Override
    public void serialize(GoalType goalType, JsonGenerator jGen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jGen.writeString(goalType.getValue());
    }

}

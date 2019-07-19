package com.hoang.lsp.core.dataformat;

import com.hoang.lsp.core.DateFormatter;
import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.dataformat.deserializer.GoalTypeDeserializer;
import com.hoang.lsp.core.dataformat.serializer.GoalTypeSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class ObjectMapperFactory {

    public ObjectMapperFactory() {

    }

    public ObjectMapper build() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(DateFormatter.get());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(GoalType.class, new GoalTypeDeserializer());
        simpleModule.addSerializer(GoalType.class, new GoalTypeSerializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}

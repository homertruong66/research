package com.hoang.lsp.core.converter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.messaging.support.GenericMessage;

public class CustomJackson2JsonMessageConverter extends Jackson2JsonMessageConverter {

    @Override
    protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) throws MessageConversionException {
        if (objectToConvert instanceof GenericMessage<?>) {
            objectToConvert = ((GenericMessage<?>) objectToConvert).getPayload();
        }
        return super.createMessage(objectToConvert, messageProperties);
    }

}

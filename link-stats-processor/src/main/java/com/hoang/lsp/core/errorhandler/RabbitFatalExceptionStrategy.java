package com.hoang.lsp.core.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;

public class RabbitFatalExceptionStrategy implements FatalExceptionStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitFatalExceptionStrategy.class);

    @Override
    public boolean isFatal(Throwable t) {
        LOGGER.error("Failed to process message from QUEUE", t);
        return true;
    }

}

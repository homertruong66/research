package com.hoang.lsp.core.errorhandler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectedHandler implements RejectedExecutionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RejectedExecutionHandler.class);

    @Override
    public void rejectedExecution (Runnable r, ThreadPoolExecutor executor) {
        try {
            LOGGER.info("Attempt to put message to Queue. Message = [{}]", ToStringBuilder.reflectionToString(r));
            LOGGER.debug("Trying to push the rejected task back to queue");
            executor.getQueue().put(r);
            LOGGER.debug("Successfully re-queue rejected task");
        }
        catch (InterruptedException e) {
            LOGGER.error("Trying to put message to Queue. Message = [{}]", ToStringBuilder.reflectionToString(r));
        }
    }
}

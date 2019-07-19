package com.hoang.lsp.task;

import com.hoang.lsp.core.MyApplicationContext;
import com.hoang.lsp.model.IncrementEvent;
import com.hoang.lsp.service.LoaderExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoaderExceptionHandlerTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoaderExceptionHandlerTask.class);

    private LoaderExceptionHandler loaderExceptionHandler;

    private List<IncrementEvent> incrementEventList;

    public LoaderExceptionHandlerTask (List<IncrementEvent> incrementEventList) {
        this.loaderExceptionHandler = MyApplicationContext.getBean("loaderExceptionHandler", LoaderExceptionHandler.class);
        this.incrementEventList = incrementEventList;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Running Loader Exception Handler Task");
            loaderExceptionHandler.writeFailedEventsToFile(incrementEventList);
        }
        catch (Exception ex) {
            LOGGER.error("Failed!", ex);
        }
    }
}

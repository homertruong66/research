package com.hoang.lsp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoang.lsp.core.MyApplicationContext;
import com.hoang.lsp.events.ClickEvent;
import com.hoang.lsp.service.LinkStatsService;

public class ClickMessageProcessingTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClickMessageProcessingTask.class);
    private static final Logger MONITOR_LOGGER = LoggerFactory.getLogger("monitorLogger");

    private ClickEvent       clickEvent;
    private long             startTime;
    private LinkStatsService linkStatsService;

    public ClickMessageProcessingTask(ClickEvent clickEvent, long startTime) {
        this.clickEvent = clickEvent;
        this.startTime = startTime;
        this.linkStatsService = MyApplicationContext.getBean("linkStatsService", LinkStatsService.class);
    }

    @Override
    public void run() {
        LOGGER.debug("Processing ClickEvent: " + clickEvent.toString());
        try {
            this.linkStatsService.doIncreaseClick(this.clickEvent);
            long endTime = System.currentTimeMillis();
            MONITOR_LOGGER.info("Click Message - Duration: " + (endTime - startTime));
        }
        catch (Exception ex) {
            LOGGER.error("Failed!", ex);
        }
    }

}

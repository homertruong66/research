package com.hoang.lsp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoang.lsp.core.MyApplicationContext;
import com.hoang.lsp.events.ConversionEvent;
import com.hoang.lsp.service.LinkStatsService;

public class ConversionMessageProcessingTask implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConversionMessageProcessingTask.class);
	private static final Logger MONITOR_LOGGER = LoggerFactory.getLogger("monitorLogger");

    private ConversionEvent  conversionEvent;
    private long             startTime;
    private LinkStatsService linkStatsService;

    public ConversionMessageProcessingTask(ConversionEvent conversionEvent, long startTime) {
        this.conversionEvent = conversionEvent;
        this.startTime = startTime;
        this.linkStatsService = MyApplicationContext.getBean("linkStatsService", LinkStatsService.class);
    }

    @Override
    public void run() {
    	LOGGER.debug("Processing ConversionEvent: " + conversionEvent.toString());
        try {
            this.linkStatsService.doIncreaseConversion(this.conversionEvent);
            long endTime = System.currentTimeMillis();
            MONITOR_LOGGER.info("Conversion Message - Duration: " + (endTime - startTime));
        }
        catch (Exception ex) {
            LOGGER.error("Failed!", ex);
        }
    }
}

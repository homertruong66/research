package com.hoang.lsp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MyApplicationContext;
import com.hoang.lsp.service.LinkStatsService;

public class ConversionLoaderTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionLoaderTask.class);

    private LinkStatsService linkStatsService;
    private GoalType         goalType;

    public ConversionLoaderTask (GoalType goalType) {
        this.linkStatsService = MyApplicationContext.getBean("linkStatsService", LinkStatsService.class);
        this.goalType = goalType;
    }

    @Override
    public void run () {
        try {
            LOGGER.debug("Loading ConversionStats...");
            this.linkStatsService.loadConversionStats(goalType);
        }
        catch (Exception ex) {
            LOGGER.error("Failed!", ex);
        }
    }

}

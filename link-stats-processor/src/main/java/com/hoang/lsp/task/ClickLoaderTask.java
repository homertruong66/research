package com.hoang.lsp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoang.lsp.core.MyApplicationContext;
import com.hoang.lsp.service.LinkStatsService;

public class ClickLoaderTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickLoaderTask.class);

    private LinkStatsService linkStatsService;

    public ClickLoaderTask() {
        this.linkStatsService = MyApplicationContext.getBean("linkStatsService", LinkStatsService.class);
    }

    @Override
    public void run() {
        try {
            LOGGER.debug("Loading ClickStats...");
            linkStatsService.loadClickStats();
        }
        catch (Exception ex) {
            LOGGER.error("Failed!", ex);
        }
    }

}

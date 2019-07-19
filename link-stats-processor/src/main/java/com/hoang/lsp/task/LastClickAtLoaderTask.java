package com.hoang.lsp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoang.lsp.service.LinkStatsService;

public class LastClickAtLoaderTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LastClickAtLoaderTask.class);

    private LinkStatsService linkStatsService;

    public LastClickAtLoaderTask(LinkStatsService linkStatsService) {
        this.linkStatsService = linkStatsService;
    }

    @Override
    public void run() {
        try {
            LOGGER.debug("Loading LastClickAt data...");
            this.linkStatsService.loadLastClickAt();
        }
        catch (Exception ex) {
            LOGGER.error("Failed!", ex);
        }
    }

}

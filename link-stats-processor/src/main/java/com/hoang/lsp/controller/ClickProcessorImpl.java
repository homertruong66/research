package com.hoang.lsp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hoang.lsp.events.ClickEvent;
import com.hoang.lsp.exception.BusinessException;
import com.hoang.lsp.service.LinkStatsService;

@Controller("clickProcessor")
public class ClickProcessorImpl implements ClickProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickProcessorImpl.class);

    @Autowired
    private LinkStatsService linkStatsService;

    @Override
    public void process (ClickEvent event) {
        LOGGER.info("Processing Click message: " + event.toString());

        try {
            this.linkStatsService.increaseClick(event);
        }
        catch (BusinessException be) {
            LOGGER.error("Error increasing Click for event: " + event.toString() + " - Cause: " + be.getMessage());
        }
    }
}

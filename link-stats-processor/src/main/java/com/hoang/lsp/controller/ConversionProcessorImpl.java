package com.hoang.lsp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hoang.lsp.events.ConversionEvent;
import com.hoang.lsp.exception.BusinessException;
import com.hoang.lsp.service.LinkStatsService;

@Controller("conversionProcessor")
public class ConversionProcessorImpl implements ConversionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionProcessorImpl.class);

    @Autowired
    private LinkStatsService linkStatsService;

    @Override
    public void process (ConversionEvent event) {
        LOGGER.info("Processing Conversion message: " + event.toString());

        try {
            this.linkStatsService.increaseConversion(event);
        }
        catch (BusinessException be) {
            LOGGER.error("Error increasing Conversion for event: " + event.toString() + " - Cause: " + be.getMessage());
        }
    }
}

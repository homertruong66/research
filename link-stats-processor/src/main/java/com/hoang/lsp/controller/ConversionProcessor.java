package com.hoang.lsp.controller;

import com.hoang.lsp.events.ConversionEvent;


public interface ConversionProcessor {

    void process(ConversionEvent event);

}

package com.hoang.lsp.service;

import com.hoang.lsp.events.ClickEvent;

public class DoNothingClickGateway implements ClickGateway {

    @Override
    public void sendClick(ClickEvent clickEvent) {

    }

}

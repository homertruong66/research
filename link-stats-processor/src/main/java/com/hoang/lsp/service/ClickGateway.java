package com.hoang.lsp.service;

import com.hoang.lsp.events.ClickEvent;


public interface ClickGateway {

    public void sendClick(ClickEvent clickEvent);

}

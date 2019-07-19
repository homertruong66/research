package com.hoang.it;

import com.hoang.lsp.events.ConversionEvent;

public interface ConversionGateway {

    public void sendConversion(ConversionEvent conversionEvent);

}

package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "getfly-server")
public class GetflyServerProperties {

    private String getAccountUrl;
    private String orderUrl;

    public String getGetAccountUrl() {
        return getAccountUrl;
    }

    public void setGetAccountUrl(String getAccountUrl) {
        this.getAccountUrl = getAccountUrl;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }
}
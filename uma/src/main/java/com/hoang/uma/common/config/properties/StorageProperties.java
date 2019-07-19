package com.hoang.uma.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties
public class StorageProperties {

    private String location = "test-location";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

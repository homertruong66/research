package com.rms.rms.common.view_model;

public class ProductSearchCriteria {

    private String channelId;

    public String getChannelId () {
        return channelId;
    }

    public void setChannelId (String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString () {
        return "ProductSearchCriteria{" +
                "channelId='" + channelId + '\'' +
                '}';
    }
}

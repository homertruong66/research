package com.rms.rms.common.view_model;

import java.io.Serializable;

/**
 * homertruong
 */

public class ShareSearchCriteria implements Serializable {

    private String channelId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "ShareSearchCriteria{" +
                "channelId='" + channelId + '\'' +
                '}';
    }
}

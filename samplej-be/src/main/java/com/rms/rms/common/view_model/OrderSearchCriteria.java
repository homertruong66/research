package com.rms.rms.common.view_model;

import java.io.Serializable;

/**
 * homertruong
 */

public class OrderSearchCriteria implements Serializable {

    private String channelId;
    private String status;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderSearchCriteria{" +
                "channelId='" + channelId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

package com.rms.rms.common.view_model;

import java.io.Serializable;

/**
 * homertruong
 */

public class PostSearchCriteria implements Serializable {

    private String categoryId;
    private String subscriberId;
    private String url;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PostSearchCriteria{" +
                "categoryId='" + categoryId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

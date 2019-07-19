package com.rms.rms.common.cache;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ShareInCache implements Serializable {

    private static final long serialVersionUID = -3247074069961822026L;

    private String affiliateId;
    private Integer clickCount;
    private Set<ClickInfoInCache> clickInfos = new HashSet<>();
    private String id;
    private String linkId;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Set<ClickInfoInCache> getClickInfos() {
        return clickInfos;
    }

    public void setClickInfos(Set<ClickInfoInCache> clickInfos) {
        this.clickInfos = clickInfos;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShareInCache{" +
                "affiliateId='" + affiliateId + '\'' +
                ", clickCount=" + clickCount +
                ", clickInfos=" + clickInfos +
                ", id='" + id + '\'' +
                ", linkId='" + linkId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

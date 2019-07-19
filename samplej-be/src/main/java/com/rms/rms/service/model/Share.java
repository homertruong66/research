package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class Share extends BaseEntityExtensible {

    private Affiliate affiliate;
    private String affiliateId;
    private Channel channel;
    private String channelId;
    private Integer clickCount;
    private Set<ClickInfo> clickInfos = new HashSet<>();
    private Link link;
    private String linkId;
    private String statsDate;
    private String url;     // store redundantly to make it easily accessible

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public Set<ClickInfo> getClickInfos() {
        return clickInfos;
    }

    public void setClickInfos(Set<ClickInfo> clickInfos) {
        this.clickInfos = clickInfos;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getStatsDate () {
        return statsDate;
    }

    public void setStatsDate (String statsDate) {
        this.statsDate = statsDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Share{" +
                ", affiliateId='" + affiliateId + '\'' +
                ", channelId=" + channelId +
                ", clickCount=" + clickCount +
                ", linkId='" + linkId + '\'' +
                ", statsDate='" + statsDate + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

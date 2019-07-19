package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class ShareDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 5091863175493171716L;

    private AffiliateDto affiliate;
    private String affiliateId;
    private String channelId;
    private Integer clickCount;
    private Set<ClickInfoDto> clickInfos = new HashSet<>();
    private String linkId;
    private String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AffiliateDto getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateDto affiliate) {
        this.affiliate = affiliate;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Set<ClickInfoDto> getClickInfos() {
        return clickInfos;
    }

    public void setClickInfos(Set<ClickInfoDto> clickInfos) {
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
        return "ShareDto{" +
                "affiliateId='" + affiliateId + '\'' +
                "channelId='" + channelId + '\'' +
                ", clickCount=" + clickCount +
                ", linkId='" + linkId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
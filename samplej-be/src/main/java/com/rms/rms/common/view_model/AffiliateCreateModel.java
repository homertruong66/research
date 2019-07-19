package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class AffiliateCreateModel extends PersonCreateModel {

    private String channelUrl;
    private String feUrl;
    private String metadata;
    private String nickname;
    private String referrer;
    private String subscriberDomainName;

    public void escapeHtml() {
        super.escapeHtml();

        this.channelUrl = HtmlUtils.htmlEscape(this.channelUrl, "UTF-8");
        this.feUrl = HtmlUtils.htmlEscape(this.feUrl, "UTF-8");
        this.nickname = HtmlUtils.htmlEscape(this.nickname, "UTF-8");
        this.referrer = HtmlUtils.htmlEscape(this.referrer, "UTF-8");
        this.subscriberDomainName = HtmlUtils.htmlEscape(this.subscriberDomainName, "UTF-8");
    }

    public String validate() {
        String errors = super.validate();
        if (errors == null) {
            errors = "";
        }

        if (StringUtils.isEmpty(feUrl)) {
            errors += "'fe_url' can't be empty! && ";
        }

        if (StringUtils.isBlank(subscriberDomainName)) {
            errors += "'subscriber_domain_name' can't be empty! && ";
        }

        if (StringUtils.isBlank(nickname)) {
            errors += "'nickname' can't be empty! && ";
        }
        else if (StringUtils.containsWhitespace(nickname)) {
            errors += "'nickname' can't contain whitespace && ";
        }

        return errors.equals("") ? null : errors;
    }

    //

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getFeUrl() {
        return feUrl;
    }

    public void setFeUrl(String feUrl) {
        this.feUrl = feUrl;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getSubscriberDomainName() {
        return subscriberDomainName;
    }

    public void setSubscriberDomainName(String subscriberDomainName) {
        this.subscriberDomainName = subscriberDomainName;
    }

    @Override
    public String toString() {
        return "AffiliateCreateModel{" +
                "channelUrl='" + channelUrl + '\'' +
                "metadata='" + metadata + '\'' +
                "nickname='" + nickname + '\'' +
                "referrer='" + referrer + '\'' +
                "feUrl='" + feUrl + '\'' +
                "subscriberDomainName='" + subscriberDomainName + '\'' +
                '}' + " - " + super.toString();
    }
}

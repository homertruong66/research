package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * homertruong
 */

public class AffiliateRemindModel {

    private String channelUrl;
    private String feUrl;
    private List<String> emails;

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(feUrl)) {
            errors += "'fe_url' can't be empty! && ";
        }

        if (emails == null || emails.size() == 0) {
            errors += "'emails' can't be null or empty! && ";
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

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "AffiliateRemindModel{" +
                "channelUrl='" + channelUrl + '\'' +
                ", feUrl='" + feUrl + '\'' +
                ", emails=" + emails +
                '}';
    }
}

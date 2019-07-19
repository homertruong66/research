package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class ChannelUpdateModel {

    private String domainName;

    public void escapeHtml() {
        this.domainName = HtmlUtils.htmlEscape(this.domainName, "UTF-8");
    }

    public String validate() {
        return null;
    }

    //
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return "ChannelCreateModel{" +
                "domainName='" + domainName + '\'' +
                '}';
    }
}

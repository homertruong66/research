package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class ChannelCreateModel {

    private String domainName;

    public void escapeHtml() {
        this.domainName = HtmlUtils.htmlEscape(this.domainName, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(domainName)) {
            errors += "'domain_name' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
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

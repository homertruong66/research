package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * homertruong
 */

public class CustomEmailModel {

    private List<String> affiliateIds;
    private String content;
    private String title;

    public String validate() {
        String errors = "";

        if (affiliateIds == null || affiliateIds.size() == 0) {
            errors += "'affiliateIds' can't be null or empty! && ";
        }
        if (StringUtils.isBlank(content)) {
            errors += "'content' can't be empty! && ";
        }
        if (StringUtils.isBlank(title)) {
            errors += "'title' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    public List<String> getAffiliateIds() {
        return affiliateIds;
    }

    public void setAffiliateIds(List<String> affiliateIds) {
        this.affiliateIds = affiliateIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CustomEmailModel{" +
                "affiliateIds=" + affiliateIds +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

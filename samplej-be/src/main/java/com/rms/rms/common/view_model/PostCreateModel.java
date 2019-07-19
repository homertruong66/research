package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

/**
 * homertruong
 */

public class PostCreateModel {

    private String categoryId;
    private String content;
    private String url;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(url)) {
            errors += "'url' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PostCreateModel{" +
                "categoryId='" + categoryId + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

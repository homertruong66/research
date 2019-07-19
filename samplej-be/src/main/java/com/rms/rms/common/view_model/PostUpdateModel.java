package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

public class PostUpdateModel {

    private String categoryId;
    private String content;

    public void escapeHtml() {
        this.content = HtmlUtils.htmlEscape(this.content, "UTF-8");
    }

    public String validate() {
        return null;
    }

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

    @Override
    public String toString() {
        return "PostUpdateModel{" +
                "categoryId='" + categoryId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

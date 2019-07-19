package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class SubsEmailTemplateUpdateModel {

    private String content;
    private Boolean isEnabled;
    private String title;

    public void escapeHtml() {
        this.title = HtmlUtils.htmlEscape(this.title, "UTF-8");
//        this.content = HtmlUtils.htmlEscape(this.content, "UTF-8");   // this is HTML
    }

    public String validate() {
        return null;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SubsEmailTemplateUpdateModel{" +
                "content='" + content + '\'' +
                ", isEnabled=" + isEnabled +
                ", title='" + title + '\'' +
                '}';
    }
}

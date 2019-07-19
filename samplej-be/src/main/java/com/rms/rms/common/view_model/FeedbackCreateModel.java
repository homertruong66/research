package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

public class FeedbackCreateModel {

    private String description;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";
        if( StringUtils.isBlank(description) ) {
            errors += "'description' can't be blank! && ";
        }

        if( StringUtils.isBlank(title) ) {
            errors += "'title' can't be blank! && ";
        }

        return errors.equals("") ? null : errors;
    }

    @Override
    public String toString() {
        return "FeedbackCreateModel{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

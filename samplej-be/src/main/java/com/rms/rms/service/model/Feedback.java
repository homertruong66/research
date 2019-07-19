package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class Feedback extends BaseEntityExtensible {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_RESOLVED = "RESOLVED";

    private String description;
    private String status;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

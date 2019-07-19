package com.rms.rms.common.view_model;

import java.util.Date;

public class SubsAlertConfigUpdateModel {

    private String content;
    private Date endDate;
    private Date startDate;
    private String title;

    public void escapeHtml() {
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SubsAlertConfigUpdateModel{" +
                "content='" + content + '\'' +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", title='" + title + '\'' +
                '}';
    }
}

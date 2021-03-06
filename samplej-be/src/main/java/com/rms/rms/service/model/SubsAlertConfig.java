package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;

public class SubsAlertConfig extends BaseEntityExtensible {

    private String content;
    private Date endDate;
    private Date startDate;
    private Subscriber subscriber;
    private String title;

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

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SubsAlertConfig{" +
                "content='" + content + '\'' +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", title='" + title + '\'' +
                '}';
    }
}

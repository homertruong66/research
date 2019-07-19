package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

public class SystemAlertDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4408620820598996393L;

    private String content;
    private Date endDate;
    private Date startDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SubsAlertConfigDto{" +
                "content='" + content + '\'' +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", title='" + title + '\'' +
                '}';
    }
}
package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class ClazzSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = -6710560257204851392L;

    @JsonProperty("startedAt")
    private Date startedAt;

    @JsonProperty("endedAt")
    private Date endedAt;

    @JsonProperty("courseId")
    private String courseId;

    @JsonProperty("teacherId")
    private String teacherId;

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "ClazzSearchCriteria{" +
                "startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                ", courseId='" + courseId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}

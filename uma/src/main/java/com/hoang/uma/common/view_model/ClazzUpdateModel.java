package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class ClazzUpdateModel implements Serializable {

    private static final long serialVersionUID = 5176167285643727682L;

    @JsonProperty
    private int duration;

    @JsonProperty
    private String room;

    @JsonProperty("startedAt")
    private Date startedAt;

    @JsonProperty("endedAt")
    private Date endedAt;

    @JsonProperty("teacherId")
    private String teacherId;

    public void escapeHtml () {
        this.room = HtmlUtils.htmlEscape(this.room);
    }

    @JsonProperty
    @Min(value = 0, message = "'Version' must be greater than or equal 0!")
    private int version;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ClazzUpdateModel{" +
                "duration=" + duration +
                ", room='" + room + '\'' +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                ", teacherId='" + teacherId + '\'' +
                ", version=" + version +
                '}';
    }
}

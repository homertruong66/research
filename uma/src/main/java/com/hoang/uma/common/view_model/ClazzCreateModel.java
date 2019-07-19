package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class ClazzCreateModel implements Serializable {

    private static final long serialVersionUID = -1908714641991122321L;

    @JsonProperty
    @Min(value = 1, message = "'duration' must be greater than 0!")
    @Max(value = 3, message = "'duration' must be less than 4!")
    private int duration;

    @JsonProperty
    @NotEmpty(message = "'Room' can't be empty!")
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

    @Override
    public String toString() {
        return "ClazzCreateModel{" +
                "duration=" + duration +
                ", room='" + room + '\'' +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}

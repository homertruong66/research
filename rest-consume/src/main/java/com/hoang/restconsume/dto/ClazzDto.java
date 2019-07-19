package com.hoang.restconsume.dto;

import java.io.Serializable;

public class ClazzDto implements Serializable {

    private static final long serialVersionUID = -5538844233273960793L;

    private String id;

    private int duration;

    private String room;

    private String startedAt;

    private String endedAt;

    private String status;

    private int version;

    private CourseDto course;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "ClazzDto{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", room='" + room + '\'' +
                ", startedAt='" + startedAt + '\'' +
                ", endedAt='" + endedAt + '\'' +
                ", status='" + status + '\'' +
                ", version=" + version +
                ", course=" + course.toString() +
                '}';
    }
}
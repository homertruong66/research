package com.hoang.uma.service.model;

import com.hoang.uma.common.enu.ClazzStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="clazzes")
public class Clazz {

    @Id
    private String id;

    @Column(name = "duration")
    private int duration;

    @Column(name = "room")
    private String room;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "ended_at")
    private Date endedAt;

    @Column(name = "status")
    private ClazzStatus status = ClazzStatus.NEW;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    // Associations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "course_id", insertable = false, updatable = false)
    private String courseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private String teacherId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clazz_id")
    private List<ClazzStudent> clazzStudents;

    // Get/Set
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

    public ClazzStatus getStatus() {
        return status;
    }

    public void setStatus(ClazzStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<ClazzStudent> getClazzStudents() {
        return clazzStudents;
    }

    public void setClazzStudents(List<ClazzStudent> clazzStudents) {
        this.clazzStudents = clazzStudents;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", room='" + room + '\'' +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }
}

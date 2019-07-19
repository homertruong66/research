package com.hvn.spring.dto;

import com.hvn.spring.model.Course;

/**
 * Created by vietnguyenq1 on 2/4/2015.
 */
public class CourseDTO {
    private Long id;
    private String name;
    private Integer credits;
    private TeacherDTO author;

    public void copyFrom(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.credits = course.getCredits();

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public TeacherDTO getAuthor() {
        return author;
    }

    public void setAuthor(TeacherDTO author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.hvn.spring.dto;

import com.hvn.spring.model.Clazz;

import java.util.Date;

/**
 * Created by vietnguyenq1 on 2/4/2015.
 */
public class ClazzDTO implements Cloneable {
    private String name;
    private Date date;
    private String schedule;
    private CourseDTO course;
    private TeacherDTO teacher;
    private StudentDTO students;

    public ClazzDTO()  {
    }

    public void copyFrom(Clazz clazz){
        if(clazz.getCourse().getId()>0){
            CourseDTO courseDTO =  new CourseDTO();
            courseDTO.copyFrom(clazz.getCourse());
            this.course = courseDTO ;
        }
        this.date = clazz.getDate();
        this.name = clazz.getName();
        this.schedule = clazz.getSchedule();
//        if(!clazz.getStudents().isEmpty()){
//            StudentDTO studentDTO= new StudentDTO();
//            studentDTO.copyFrom(clazz.getStudents());
//        }
        if(clazz.getTeacher().getId()>0){
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.copyFrom(clazz.getTeacher());
            this.teacher = teacherDTO;
        }

    }

    public void copyTo(Clazz clazz){
        clazz.setDate(this.date);
        clazz.setName(this.name);
        clazz.setSchedule(this.schedule);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public StudentDTO getStudents() {
        return students;
    }

    public void setStudents(StudentDTO students) {
        this.students = students;
    }
}

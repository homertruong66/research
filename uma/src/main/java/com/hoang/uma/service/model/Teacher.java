package com.hoang.uma.service.model;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    @Column(name = "degree")
    private String degree;

    // Associations
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private List<DepartmentTeacher> departmentTeachers;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private List<Clazz> clazzes;

    // Get/Set
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<DepartmentTeacher> getDepartmentTeachers() {
        return departmentTeachers;
    }

    public void setDepartmentTeachers(List<DepartmentTeacher> departmentTeachers) {
        this.departmentTeachers = departmentTeachers;
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "degree='" + degree + '\'' +
                '}' + super.toString();
    }
}

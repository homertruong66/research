package com.hoang.uma.service.model;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @Column(name = "year")
    private int year;

    // Associations
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "department_id", insertable = false, updatable = false)
    private String departmentId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<ClazzStudent> clazzStudents;

    // Get/Set
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<ClazzStudent> getClazzStudents() {
        return clazzStudents;
    }

    public void setClazzStudents(List<ClazzStudent> clazzStudents) {
        this.clazzStudents = clazzStudents;
    }

    @Override
    public String toString() {
        return "Student{" +
                "year=" + year +
                '}' + super.toString();
    }
}

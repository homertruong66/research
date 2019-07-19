package com.hoang.uma.service.model;

import javax.persistence.*;

@Entity
@Table(
    name="departments_teachers",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"department_id", "teacher_id"})
    }
)
public class DepartmentTeacher {

    @Id
    private String id;

    // Associations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // Get/Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

//    @Override
//    public String toString() {
//        return department.getName();
//    }
}

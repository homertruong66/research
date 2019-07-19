package com.hoang.restconsume.dto;

import java.io.Serializable;

public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1818432801423902005L;

    private String id;

    private String name;

    private int numberOfCredits;

    private int version;

    private DepartmentDto department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", numberOfCredits=" + numberOfCredits +
                ", version=" + version +
                ", department='" + department.toString() + '\'' +
                '}';
    }
}
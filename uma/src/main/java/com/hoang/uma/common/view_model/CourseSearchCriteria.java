package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */

public class CourseSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = -2833672929426025047L;

    @JsonProperty
    private String name;

    @JsonProperty("departmentId")
    private String departmentId;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "CourseSearchCriteria{" +
                "name='" + name + '\'' +
                ", departmentId='" + departmentId + '\'' +
                '}';
    }
}

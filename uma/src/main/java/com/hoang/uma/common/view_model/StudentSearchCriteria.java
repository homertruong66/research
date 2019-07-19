package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */

public class StudentSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 4798571438681642861L;

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
        return "StudentSearchCriteria{" +
                "name='" + name + '\'' +
                ", departmentId='" + departmentId + '\'' +
                '}' + super.toString();
    }
}

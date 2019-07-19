package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */

public class DepartmentSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 7906542711359165495L;

    @JsonProperty
    private String name;

    @JsonProperty("universityId")
    private String universityId;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    @Override
    public String toString() {
        return "DepartmentSearchCriteria{" +
                "name='" + name + '\'' +
                ", universityId='" + universityId + '\'' +
                '}';
    }
}

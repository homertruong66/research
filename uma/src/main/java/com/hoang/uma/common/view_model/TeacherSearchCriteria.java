package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */

public class TeacherSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 3918286315679973037L;

    @JsonProperty
    private String name;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeacherSearchCriteria{" +
                "name='" + name + '\'' +
                '}' + " - " + super.toString();
    }
}

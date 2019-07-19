package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */

public class UserSearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = 4279351040702774201L;

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
        return "UserSearchCriteria{" +
                "name='" + name + '\'' +
                '}' + " - " + super.toString();
    }
}

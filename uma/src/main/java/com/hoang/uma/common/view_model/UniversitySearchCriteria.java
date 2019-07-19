package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */

public class UniversitySearchCriteria extends SearchCriteria implements Serializable {

    private static final long serialVersionUID = -185320801671902445L;

    @JsonProperty
    private String name;

    @JsonProperty("countryId")
    private String countryId;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "UniversitySearchCriteria{" +
                "name='" + name + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}

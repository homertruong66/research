package com.hoang.srj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoang.srj.model.University;

import java.io.Serializable;

public class UniversityDto implements Serializable {

    private static final long serialVersionUID = 7417941448877300898L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty
    private String createdAt;

    @JsonProperty
    private String updatedAt;

    public void copyFrom(University university) {
        this.id = university.getId();
        this.name = university.getName();
        this.createdAt = university.getCreatedAt().toString();
        this.updatedAt = university.getUpdatedAt().toString();
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
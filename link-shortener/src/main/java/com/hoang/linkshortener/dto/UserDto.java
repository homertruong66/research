package com.hoang.linkshortener.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoang.linkshortener.model.User;
import com.hoang.linkshortener.util.DateUtils;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 7417941448877300898L;

    @JsonProperty("id")
    private int id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

    @JsonProperty("level")
    private int level;

    @JsonProperty
    private String createdAt;

    @JsonProperty
    private String updatedAt;

    public void copyFrom (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.level = user.getLevel();
        this.createdAt = DateUtils.zonedDateTimeToISO8601(user.getCreatedAt());
        this.updatedAt = DateUtils.zonedDateTimeToISO8601(user.getUpdatedAt());
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public int getLevel () {
        return level;
    }

    public void setLevel (int level) {
        this.level = level;
    }

    public String getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
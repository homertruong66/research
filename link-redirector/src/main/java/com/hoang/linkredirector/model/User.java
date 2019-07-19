package com.hoang.linkredirector.model;

import java.time.ZonedDateTime;

public class User {

    private int           id;
    private String        email;
    private String        name;
    private String        password;
    private int           level;
    private String        status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

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

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

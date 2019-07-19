package com.hoang.jerseyspringjdbc.view_model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoang.jerseyspringjdbc.model.User;

public class UserCreateModel implements Serializable {

    private static final long serialVersionUID = 6379951391691475123L;

    @NotNull(message = "'email' can't be null")
    private String email;

    @NotNull(message = "'name' can't be null")
    private String name;

    @NotNull(message = "'password' can't be null")
    private String password;

    @Min(value = 1, message = "'level' must be greater than 0")
    @Max(value = Integer.MAX_VALUE, message = "'level' must be less than " + Integer.MAX_VALUE)
    @JsonProperty("level")
    private int level;

    @JsonProperty("status")
    private String status = "active";

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

    public void copyTo (User user) {
        Assert.notNull(user);
        user.setEmail(this.getEmail());
        user.setName(this.getName());
        user.setPassword(this.getPassword());
        user.setLevel(this.level);
        user.setStatus(this.status);
    }
}

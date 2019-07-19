package com.hoang.srj.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoang.srj.model.User;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserCreateModel implements Serializable {

    private static final long serialVersionUID = 4554937659178836353L;

    @NotNull(message = "'Email' can't be null!")
    private String email;

    @NotNull(message = "'Name' can't be null!")
    private String name;

    @NotNull(message = "'Password' can't be null!")
    private String password;

    @Min(value = 1, message = "'Age' must be greater than 0!")
    @Max(value = 123, message = "'Age' must be less than 123!")
    @JsonProperty("age")
    private int age = 18;

    @NotNull(message = "User must have at least one role!")
    private String roles;

    public void copyTo (User user) {
        Assert.notNull(user);
        user.setEmail(this.getEmail());
        user.setName(this.getName());
        user.setPassword(this.getPassword());
        user.setAge(this.getAge());
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserCreateModel{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", roles='" + roles + '\'' +
                '}';
    }
}

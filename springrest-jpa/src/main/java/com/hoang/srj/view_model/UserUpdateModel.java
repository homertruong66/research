package com.hoang.srj.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoang.srj.model.User;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserUpdateModel implements Serializable {

    private static final long serialVersionUID = -1922541490754776621L;

    @NotNull(message = "User Id must be specified!")
    private String id;

    private String name;

    @Min(value = 1, message = "'Age' must be greater than 0!")
    @Max(value = 123, message = "'Age' must be less than 123!")
    @JsonProperty("age")
    private int age;

    private String roles;

    public void copyTo (User user) {
        Assert.notNull(user);
        user.setName(this.getName());
        user.setAge(this.getAge());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
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
        return "UserUpdateModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", roles='" + roles + '\'' +
                '}';
    }
}

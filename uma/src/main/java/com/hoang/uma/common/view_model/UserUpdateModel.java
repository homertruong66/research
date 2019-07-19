package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

/**
 * homertruong
 */

public class UserUpdateModel implements Serializable {

    private static final long serialVersionUID = -1922541490754776621L;

    @JsonProperty
    private String name;

    @JsonProperty
    @Min(value = 1, message = "'Age' must be greater than 0!")
    @Max(value = 123, message = "'Age' must be less than 123!")
    private int age;

    @JsonProperty
    @Min(value = 0, message = "'Version' must be greater than or equal 0!")
    private int version;

    @JsonProperty
    private List<String> roles;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UserUpdateModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", version=" + version +
                ", roles=" + roles +
                '}';
    }
}

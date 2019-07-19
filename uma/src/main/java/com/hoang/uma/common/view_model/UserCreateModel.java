package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * homertruong
 */

public class UserCreateModel implements Serializable {

    private static final long serialVersionUID = 4554937659178836353L;

    @JsonProperty
    @NotEmpty(message = "'Email' can't be empty!")
    @Email(message = "Invalid email format!")
    private String email;

    @JsonProperty("confirmedEmail")
    @NotEmpty(message = "'Confirmed Email' can't be empty!")
    @Email(message = "Invalid email format!")
    private String confirmedEmail;

    @JsonProperty
    @NotEmpty(message = "'Name' can't be empty!")
    private String name;

    @JsonProperty
    @NotEmpty(message = "'Password' can't be empty!")
    @Size(min=8, max=25)
    private String password;

    @JsonProperty("confirmedPassword")
    @NotEmpty(message = "'Confirmed Password' can't be empty!")
    @Size(min=8, max=25)
    private String confirmedPassword;

    @JsonProperty
    @Min(value = 1, message = "'Age' must be greater than 0!")
    @Max(value = 123, message = "'Age' must be less than 123!")
    private int age = 18;

    @JsonProperty
    @NotNull(message = "User must have at least one role!")
    private List<String> roles;

    @AssertTrue(message="Email fields should be matched!")
    private boolean isEmailsMatched() {
        return this.email.equals(this.confirmedEmail);
    }

    @AssertTrue(message="Password fields should be matched!")
    private boolean isPasswordsMatched() {
        return this.password.equals(this.confirmedPassword);
    }

    public void escapeHtml () {
        this.email = HtmlUtils.htmlEscape(this.email);
        this.name = HtmlUtils.htmlEscape(this.name);
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(String confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
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

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
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

package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.Set;

/**
 * homertruong
 */

public class PersonCreateModel {

    private final int MIN_PASSWORD_LENGTH = 8;
    private final int MAX_PASSWORD_LENGTH = 28;

    // Person
    private String avatar;
    private String firstName;
    private String lastName;
    private String phone;

    // Admin

    // User
    private String email;
    private String confirmedEmail;
    private String password;
    private String confirmedPassword;
    private Set<String> roles;

    public void escapeHtml() {
        // Person
        this.avatar = HtmlUtils.htmlEscape(this.avatar, "UTF-8");
        this.firstName = HtmlUtils.htmlEscape(this.firstName, "UTF-8");
        this.lastName = HtmlUtils.htmlEscape(this.lastName, "UTF-8");
        this.phone = HtmlUtils.htmlEscape(this.phone, "UTF-8");

        // Admin
    }

    protected String validate() {
        String errors = "";

        if (StringUtils.isBlank(firstName)) {
            errors += "'first_name' can't be empty! && ";
        }

        if (StringUtils.isBlank(lastName)) {
            errors += "'last_name' can't be empty! && ";
        }

        if (StringUtils.isBlank(email)) {
            errors += "'email' can't be empty! && ";
        }

        if (!MyStringUtil.isEmailFormatCorrect(email)) {
            errors += "'email' has incorrect format! && ";
        }

        if (!MyStringUtil.isEmailFormatCorrect(confirmedEmail)) {
            errors += "'confirmed_email' has incorrect format! && ";
        }

        if (email == null || confirmedEmail == null || !email.equals(this.confirmedEmail)) {
            errors += "'email' and 'confirmed_email' must be matched! && ";
        }

        if (StringUtils.isBlank(password)) {
            errors += "'password' can't be empty! && ";
        }
        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            errors += "'password' length must between 8 and 28 characters! && ";
        }

        if (StringUtils.isBlank(confirmedPassword)) {
            errors += "'confirmed_password' can't be empty! && ";
        }

        if (password == null || confirmedPassword == null || !password.equals(this.confirmedPassword)) {
            errors += "'password' and 'confirmed_password' must be matched! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(String confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "PersonCreateModel{" +
                "avatar='" + avatar + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", confirmedEmail='" + confirmedEmail + '\'' +
                ", password='" + password + '\'' +
                ", confirmedPassword='" + confirmedPassword + '\'' +
                ", roles=" + roles +
                '}';
    }
}

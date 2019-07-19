package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class User extends BaseEntityExtensible {

    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_PROFILE_UPDATED = "PROFILE_UPDATED";
    public static final int MINIMUM_PASSWORD_LENGTH = 8;

    private String activationCode;
    private String email;
    private String password;
    private Person person;
    private Set<Role> roles = new HashSet<>(0);
    private String status;
    private String token;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "activationCode='" + activationCode + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", status='" + status + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class UserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -7672783910959655123L;

    /**
     * transient keyword is used to exclude properties in gson Serialization
     * https://sites.google.com/site/gson/gson-user-guide#TOC-Finer-Points-with-Objects
     */

    private transient String activationCode;
    private String email;
    private transient String password;
    private Set<String> roles = new HashSet<>(0);
    private String status;
    private transient String token;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
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
        return "UserDto{" +
                "activationCode='" + activationCode + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", status='" + status + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

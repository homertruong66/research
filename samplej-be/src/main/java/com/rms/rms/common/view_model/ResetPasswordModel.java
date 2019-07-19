package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class ResetPasswordModel {

    private String email;
    private String feUrl;

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getFeUrl () {
        return feUrl;
    }

    public void setFeUrl (String feUrl) {
        this.feUrl = feUrl;
    }

    @Override
    public String toString () {
        return "ResetPasswordModel{" +
               "email='" + email + '\'' +
               ", feUrl='" + feUrl + '\'' +
               '}';
    }
}

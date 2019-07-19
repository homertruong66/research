package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

public class AffiliateSignInModel extends PersonUpdateModel {

    private String email;
    private String password;

    public void escapeHtml() {
        this.email = HtmlUtils.htmlEscape(this.email, "UTF-8");
        this.password = HtmlUtils.htmlEscape(this.password, "UTF-8");
    }

    public String validate() {
        String errors = "";
        if (email == null) {
            errors += "'email' can't be null! && ";
        }

        if (password == null) {
            errors += "'password' can't be null! && ";
        }

        return errors.equals("") ? null : errors;
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

    @Override
    public String toString() {
        return "AffiliateSignInModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

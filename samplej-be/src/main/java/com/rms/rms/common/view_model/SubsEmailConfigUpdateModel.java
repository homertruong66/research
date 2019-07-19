package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class SubsEmailConfigUpdateModel {

    private String email;
    private String emailReplyTo;
    private String hostname;
    private String password;
    private Integer port;

    public void escapeHtml() {
        this.hostname = HtmlUtils.htmlEscape(this.hostname, "UTF-8");
        this.password = HtmlUtils.htmlEscape(this.password, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (email != null) {
            if (!MyStringUtil.isEmailFormatCorrect(email)) {
                errors += "'email' has incorrect format! && ";
            }
        }

        if (emailReplyTo != null) {
            if (!MyStringUtil.isEmailFormatCorrect(emailReplyTo)) {
                errors += "'email_reply_to' has incorrect format! && ";
            }
        }

        return errors.equals("") ? null : errors;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailReplyTo() {
        return emailReplyTo;
    }

    public void setEmailReplyTo(String emailReplyTo) {
        this.emailReplyTo = emailReplyTo;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "SubsEmailConfigUpdateModel{" +
                "email='" + email + '\'' +
                ", emailReplyTo='" + emailReplyTo + '\'' +
                ", hostname='" + hostname + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                '}';
    }
}

package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Set;

/**
 * homertruong
 */

public class SubsEmailConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 5499964989062333981L;

    // Subscriber Info
    private String email;
    private String emailReplyTo;
    private String hostname;
    private String password;
    private Integer port;
    private String subscriberId;
    private Set<SubsEmailTemplateDto> subsEmailTemplates;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Set<SubsEmailTemplateDto> getSubsEmailTemplates() {
        return subsEmailTemplates;
    }

    public void setSubsEmailTemplates(Set<SubsEmailTemplateDto> subsEmailTemplates) {
        this.subsEmailTemplates = subsEmailTemplates;
    }

    @Override
    public String toString() {
        return "SubsEmailConfigDto{" +
                "email='" + email + '\'' +
                ", emailReplyTo='" + emailReplyTo + '\'' +
                ", hostname='" + hostname + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
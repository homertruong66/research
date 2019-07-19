package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class SubsEmailConfig extends BaseEntityExtensible {

    private String email;
    private String emailReplyTo;
    private String hostname;
    private String password;
    private Integer port;
    private Subscriber subscriber;
    private Set<SubsEmailTemplate> subsEmailTemplates = new HashSet<>();

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

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Set<SubsEmailTemplate> getSubsEmailTemplates() {
        return subsEmailTemplates;
    }

    public void setSubsEmailTemplates(Set<SubsEmailTemplate> subsEmailTemplates) {
        this.subsEmailTemplates = subsEmailTemplates;
    }

    @Override
    public String toString() {
        return "SubsEmailConfig{" +
                "email='" + email + '\'' +
                ", emailReplyTo='" + emailReplyTo + '\'' +
                ", hostname='" + hostname + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                '}';
    }
}

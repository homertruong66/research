package com.rms.rms.service.model;

import java.util.HashMap;
import java.util.Map;

/**
 * homertruong
 */

public class Email {

    private String fromName;
    private String fromEmail;
    private String html; //content html
    private String text; //content text
    private Email.Recipient to;
    private String bcc;
    private String subject;
    private String templateName;
    private Map<String, Object> templateParams = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private Boolean async = false;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Recipient getTo() {
        return to;
    }

    public void setTo(Recipient to) {
        this.to = to;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, Object> templateParams) {
        this.templateParams = templateParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    @Override
    public String toString() {
        return "Email{" +
                "fromName='" + fromName + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", to=" + to +
                ", bcc=" + bcc +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateParams=" + templateParams +
                ", headers=" + headers +
                ", async=" + async +
                '}';
    }

    public static class Recipient {

        private String email;
        private String name;
        private Email.Recipient.Type type;

        public Recipient() {
            this.type = Email.Recipient.Type.TO;
        }

        public Email.Recipient.Type getType() {
            return this.type;
        }

        public void setType(Email.Recipient.Type type) {
            this.type = type;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public enum Type {
            TO,
            BCC,
            CC
        }

        @Override
        public String toString() {
            return "Recipient{" +
                    "email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}

package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "email-server")
public class EmailServerProperties {

    private LocalEmailProperties localEmail;
    private MandrillEmailProperties mandrillEmail;
    private SendgridEmailProperties sendgridEmail;

    public LocalEmailProperties getLocalEmail() {
        return localEmail;
    }

    public void setLocalEmail(LocalEmailProperties localEmail) {
        this.localEmail = localEmail;
    }

    public MandrillEmailProperties getMandrillEmail() {
        return mandrillEmail;
    }

    public void setMandrillEmail(MandrillEmailProperties mandrillEmail) {
        this.mandrillEmail = mandrillEmail;
    }

    public SendgridEmailProperties getSendgridEmail() {
        return sendgridEmail;
    }

    public void setSendgridEmail(SendgridEmailProperties sendgridEmail) {
        this.sendgridEmail = sendgridEmail;
    }

    @ConfigurationProperties(prefix = "email-server.local-email")
    public static class LocalEmailProperties {
        private String host;
        private String email;
        private String password;
        private Integer port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
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

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

    @ConfigurationProperties(prefix = "email-server.mandrill-email")
    public static class MandrillEmailProperties {
        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

    @ConfigurationProperties(prefix = "email-server.sendgrid-email")
    public static class SendgridEmailProperties {
        private String apiKey;
        private String email;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
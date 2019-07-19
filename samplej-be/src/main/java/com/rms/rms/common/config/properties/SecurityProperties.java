package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private JwtProperties jwt;

    private CorsProperties cors;

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public CorsProperties getCors() {
        return cors;
    }

    public void setCors(CorsProperties cors) {
        this.cors = cors;
    }

    @ConfigurationProperties(prefix = "security.jwt")
    public static class JwtProperties {

        private int expiration;
        private String secret;

        public int getExpiration() {
            return expiration;
        }

        public void setExpiration(int expiration) {
            this.expiration = expiration;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    @ConfigurationProperties(prefix = "security.cors")
    public static class CorsProperties {

        private String allowedExposedHeaders;
        private String allowedHeaders;
        private String allowedMethods;
        private String allowedOrigins;
        private Boolean isAllowedCredentials;
        private Boolean isEnabled;

        public String getAllowedExposedHeaders() {
            return allowedExposedHeaders;
        }

        public void setAllowedExposedHeaders(String allowedExposedHeaders) {
            this.allowedExposedHeaders = allowedExposedHeaders;
        }

        public String getAllowedHeaders() {
            return allowedHeaders;
        }

        public void setAllowedHeaders(String allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
        }

        public String getAllowedMethods() {
            return allowedMethods;
        }

        public void setAllowedMethods(String allowedMethods) {
            this.allowedMethods = allowedMethods;
        }

        public String getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(String allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public Boolean getIsAllowedCredentials() {
            return this.isAllowedCredentials;
        }

        public void setIsAllowedCredentials(Boolean isAllowedCredentials) {
            this.isAllowedCredentials = isAllowedCredentials;
        }

        public Boolean getIsEnabled() {
            return this.isEnabled;
        }

        public void setIsEnabled(Boolean isEnabled) {
            this.isEnabled = isEnabled;
        }
    }

}
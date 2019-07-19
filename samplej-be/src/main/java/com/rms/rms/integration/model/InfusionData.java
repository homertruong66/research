package com.rms.rms.integration.model;

public class InfusionData {

    private String accessToken;
    private int expiresInSeconds;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(int expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "InfusionData{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresInSeconds=" + expiresInSeconds +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
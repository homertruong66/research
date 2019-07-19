package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class Bank extends BaseEntityExtensible {

    private Affiliate affiliate;
    private String branch;
    private String name;
    private String number;
    private String username;

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "branch='" + branch + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
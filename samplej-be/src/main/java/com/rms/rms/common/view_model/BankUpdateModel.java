package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

public class BankUpdateModel {

    private String name;
    private String branch;
    private String username;
    private String number;

    public void escapeHtml() {
        this.name = HtmlUtils.htmlEscape(this.name, "UTF-8");
        this.branch = HtmlUtils.htmlEscape(this.branch, "UTF-8");
        this.username = HtmlUtils.htmlEscape(this.username, "UTF-8");
        this.number = HtmlUtils.htmlEscape(this.number, "UTF-8");
    }

    public String validate() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BankUpdateModel{" +
                "name='" + name + '\'' +
                ", branch='" + branch + '\'' +
                ", username='" + username + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

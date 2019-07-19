package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.Set;

/**
 * homertruong
 */

public class PersonUpdateModel {

    // Person
    private String address;
    private String avatar;
    private BankUpdateModel bank;
    private Date birthday;
    private String firstName;
    private String gender;
    private String lastName;
    private String phone;
    private String provinceId;

    // Admin

    // User
    private Set<String> roles;

    public void escapeHtml() {
        // Person
        this.address = HtmlUtils.htmlEscape(this.address, "UTF-8");
        this.avatar = HtmlUtils.htmlEscape(this.avatar, "UTF-8");
        if (bank != null) {
            bank.escapeHtml();
        }
        this.firstName = HtmlUtils.htmlEscape(this.firstName, "UTF-8");
        this.gender = HtmlUtils.htmlEscape(this.gender, "UTF-8");
        this.lastName = HtmlUtils.htmlEscape(this.lastName, "UTF-8");
        this.phone = HtmlUtils.htmlEscape(this.phone, "UTF-8");
        this.provinceId = HtmlUtils.htmlEscape(this.provinceId, "UTF-8");
        // Admin
    }

    public String validate() {
        return null;
    }

    //
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BankUpdateModel getBank() {
        return bank;
    }

    public void setBank(BankUpdateModel bank) {
        this.bank = bank;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "PersonUpdateModel{" +
                "address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", birthday=" + birthday +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", roles=" + roles +
                '}';
    }
}

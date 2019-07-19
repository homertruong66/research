package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class PersonDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 402608155252106218L;

    public static final String TYPE_ADMIN = "Admin";
    public static final String TYPE_AFFILIATE = "Affiliate";
    public static final String TYPE_CHANNEL = "Channel";
    public static final String TYPE_SUBS_ADMIN = "SubsAdmin";

    private String address;
    private String avatar;
    private BankDto bank;
    private Date birthday;
    private transient String discriminator;
    private String firstName;
    private String gender;
    private String lastName;
    private String phone;
    private String provinceId;
    private UserDto user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public BankDto getBank() {
        return bank;
    }

    public void setBank(BankDto bank) {
        this.bank = bank;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", bank='" + bank + '\'' +
                ", birthday=" + birthday +
                ", discriminator='" + discriminator + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", user=" + user +
                '}';
    }
}
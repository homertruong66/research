package com.rms.rms.common.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class LoginDto implements Serializable {

    private static final long serialVersionUID = -5922591658492446043L;

    private String id;
    private String avatar;
    private String email;
    private String firstName;
    private Boolean isPhoneVerified;
    private String lastName;
    private String nickname;
    private String phone;
    private Set<String> roles = new HashSet<>(0);
    private String status;
    private String subscriberDomainName;
    private String subscriberId;

    // for SubsAdmin
    private PackageConfigAppliedDto currentPackage;

    // for only login Affiliate from specific Channel
    private String confirmMessage;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(Boolean isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscriberDomainName() {
        return subscriberDomainName;
    }

    public void setSubscriberDomainName(String subscriberDomainName) {
        this.subscriberDomainName = subscriberDomainName;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public PackageConfigAppliedDto getCurrentPackage() {
        return currentPackage;
    }

    public void setCurrentPackage(PackageConfigAppliedDto currentPackage) {
        this.currentPackage = currentPackage;
    }

    // for only login Affiliate from specific Channel
    public String getConfirmMessage() {
        return confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", isPhoneVerified='" + isPhoneVerified + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                ", status='" + status + '\'' +
                ", subscriberDomainName='" + subscriberDomainName + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", confirmMessage='" + confirmMessage + '\'' +
                '}';
    }
}
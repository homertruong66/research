package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class SubsEmailTemplate extends BaseEntityExtensible {

    public static final String TYPE_AFF_SIGN_UP = "AFF_SIGN_UP";
    public static final String TYPE_AFF_REMIND_UPDATE_BANK_INFO = "AFF_REMIND_UPDATE_BANK_INFO";
    public static final String TYPE_AFF_FORGOT_PASSWORD = "AFF_FORGOT_PASSWORD";
    public static final String TYPE_CUSTOMER_ORDER_CREATED = "CUSTOMER_ORDER_CREATED";
    public static final String TYPE_AFF_ORDER_CREATED = "AFF_ORDER_CREATED";
    public static final String TYPE_AFF_ORDER_APPROVED = "AFF_ORDER_APPROVED";
    public static final String TYPE_AFF_PAYMENT_APPROVED = "AFF_PAYMENT_APPROVED";

    private String content;
    private Boolean isEnabled;
    private String subsEmailConfigId;
    private String title;
    private String type;

    public static boolean checkType(String type) {
        return  type.equals(TYPE_AFF_SIGN_UP) ||
                type.equals(TYPE_AFF_REMIND_UPDATE_BANK_INFO) ||
                type.equals(TYPE_AFF_FORGOT_PASSWORD) ||
                type.equals(TYPE_CUSTOMER_ORDER_CREATED) ||
                type.equals(TYPE_AFF_ORDER_CREATED) ||
                type.equals(TYPE_AFF_ORDER_APPROVED) ||
                type.equals(TYPE_AFF_PAYMENT_APPROVED);
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubsEmailConfigId() {
        return subsEmailConfigId;
    }

    public void setSubsEmailConfigId(String subsEmailConfigId) {
        this.subsEmailConfigId = subsEmailConfigId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubsEmailTemplate{" +
                "type='" + type + '\'' +
                ", isEnabled=" + isEnabled +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

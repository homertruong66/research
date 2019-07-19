package com.rms.rms.common.dto;

import java.io.Serializable;

/**
 * homertruong
 */

public class SubsAdminDto extends PersonDto implements Serializable {

    private static final long serialVersionUID = -8240005328363154473L;

    private Boolean isRootSubsAdmin;
    private String subscriberId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getIsRootSubsAdmin() {
        return isRootSubsAdmin;
    }

    public void setIsRootSubsAdmin(Boolean isRootSubsAdmin) {
        this.isRootSubsAdmin = isRootSubsAdmin;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "SubsAdminDto{" +
                "subscriberId='" + subscriberId + '\'' +
                '}' + " - " + super.toString();
    }
}
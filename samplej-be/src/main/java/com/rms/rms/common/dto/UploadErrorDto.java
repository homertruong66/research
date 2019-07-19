package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class UploadErrorDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1941644220533223102L;

    private String domainObjectType;
    private String error;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDomainObjectType() {
        return domainObjectType;
    }

    public void setDomainObjectType(String domainObjectType) {
        this.domainObjectType = domainObjectType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UploadErrorDto{" +
                "error='" + error + '\'' +
                ", domainObjectType='" + domainObjectType + '\'' +
                '}';
    }
}

package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class UploadError extends BaseEntityExtensible {

    private String domainObjectType;
    private String error;

    public String getDomainObjectType() {
        return domainObjectType;
    }

    public void setDomainObjectType(String domainObjectType) {
        this.domainObjectType = domainObjectType;
    }

    public String getError () {
        return error;
    }

    public void setError (String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UploadError{" +
                "error='" + error + '\'' +
                ", domainObjectType='" + domainObjectType + '\'' +
                '}';
    }

}

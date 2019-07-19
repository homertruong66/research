package com.rms.rms.common.view_model;

import java.io.Serializable;

public class UploadErrorSearchCriteria implements Serializable {

    private String domainObjectType;

    public String getDomainObjectType() {
        return domainObjectType;
    }

    public void setDomainObjectType(String domainObjectType) {
        this.domainObjectType = domainObjectType;
    }

    @Override
    public String toString () {
        return "ErrorSearchCriteria{" +
                ", domainObjectType='" + domainObjectType + '\'' +
                '}';
    }
}

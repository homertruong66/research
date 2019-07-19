package com.hoang.srj.dto;

import java.io.Serializable;

public class HealthDto implements Serializable {

    private static final long serialVersionUID = -7586400894815571701L;

    private String appStatus;
    private String dbMasterStatus;
    private String dbSlaveStatus;

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getDbMasterStatus() {
        return dbMasterStatus;
    }

    public void setDbMasterStatus(String dbMasterStatus) {
        this.dbMasterStatus = dbMasterStatus;
    }

    public String getDbSlaveStatus() {
        return dbSlaveStatus;
    }

    public void setDbSlaveStatus(String dbSlaveStatus) {
        this.dbSlaveStatus = dbSlaveStatus;
    }

}

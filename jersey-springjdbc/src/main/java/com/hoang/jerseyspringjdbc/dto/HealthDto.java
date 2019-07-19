package com.hoang.jerseyspringjdbc.dto;

public class HealthDto {

    private String jerseyStatus;
    private String modelDbMasterStatus;
    private String modelDbSlaveStatus;

    public String getJerseyStatus () {
        return jerseyStatus;
    }

    public void setJerseyStatus (String jerseyStatus) {
        this.jerseyStatus = jerseyStatus;
    }

    public String getModelDbMasterStatus () {
        return modelDbMasterStatus;
    }

    public void setModelDbMasterStatus (String modelDbMasterStatus) {
        this.modelDbMasterStatus = modelDbMasterStatus;
    }

    public String getModelDbSlaveStatus () {
        return modelDbSlaveStatus;
    }

    public void setModelDbSlaveStatus (String modelDbSlaveStatus) {
        this.modelDbSlaveStatus = modelDbSlaveStatus;
    }

}

package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class CategoryDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 2281716278559456765L;

    private String name;
    private String subsAdminId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubsAdminId() {
        return subsAdminId;
    }

    public void setSubsAdminId(String subsAdminId) {
        this.subsAdminId = subsAdminId;
    }


    @Override
    public String toString() {
        return "CategoryDto{" +
                "name='" + name + '\'' +
                ", subsAdminId='" + subsAdminId + '\'' +
                '}';
    }
}
package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class SubsGetResponseConfigTestDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1458018172019031050L;

    private boolean success;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "SubsGetResponseConfigTestDto{" +
                "success=" + success +
                '}';
    }
}
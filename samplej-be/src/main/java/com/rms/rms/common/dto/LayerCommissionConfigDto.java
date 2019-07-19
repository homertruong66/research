package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class LayerCommissionConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1736186761425023120L;

    private Double commission;
    private Integer layer;
    private String subsCommissionConfigId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSubsCommissionConfigId() {
        return subsCommissionConfigId;
    }

    public void setSubsCommissionConfigId(String subsCommissionConfigId) {
        this.subsCommissionConfigId = subsCommissionConfigId;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    @Override
    public String toString() {
        return "LayerCommissionConfigDto{" +
                "subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
                ", commission=" + commission +
                ", layer=" + layer +
                '}';
    }
}
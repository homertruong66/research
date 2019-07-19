package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class LayerCommissionConfig extends BaseEntityExtensible {

    private Double commission;
    private Integer layer;
    private String subsCommissionConfigId;

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
        return "LayerCommissionConfig{" +
                "subsCommissionConfigId=" + subsCommissionConfigId + '\'' +
                ", commission=" + commission +
                ", layer=" + layer +
                '}';
    }
}

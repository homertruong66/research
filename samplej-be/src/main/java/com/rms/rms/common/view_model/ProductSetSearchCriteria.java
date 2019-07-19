package com.rms.rms.common.view_model;

public class ProductSetSearchCriteria {

    private String subsCommissionConfigId;

    public String getSubsCommissionConfigId() {
        return subsCommissionConfigId;
    }

    public void setSubsCommissionConfigId(String subsCommissionConfigId) {
        this.subsCommissionConfigId = subsCommissionConfigId;
    }

    @Override
    public String toString() {
        return "ProductSetSearchCriteria{" +
                "subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
                '}';
    }

}

package com.rms.rms.common.view_model;

import java.io.Serializable;

public class PriorityGroupSearchCriteria implements Serializable {

    private static final long serialVersionUID = 4838199292529651767L;

    private String subsCommissionConfigId;

    public String getSubsCommissionConfigId() {
        return subsCommissionConfigId;
    }

    public void setSubsCommissionConfigId(String subsCommissionConfigId) {
        this.subsCommissionConfigId = subsCommissionConfigId;
    }

    @Override
    public String toString () {
        return "PriorityGroupSearchCriteria{" +
               "subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
               '}';
    }

}

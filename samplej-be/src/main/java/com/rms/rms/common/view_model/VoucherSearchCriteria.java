package com.rms.rms.common.view_model;

import java.io.Serializable;

public class VoucherSearchCriteria implements Serializable {

    private static final long serialVersionUID = -4402154860437087369L;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VoucherSearchCriteria{" +
                "status='" + status + '\'' +
                '}';
    }
}

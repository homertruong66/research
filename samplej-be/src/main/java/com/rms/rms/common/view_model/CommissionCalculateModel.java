package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

/**
 * homertruong
 */

public class CommissionCalculateModel {

    private String orderId;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(orderId)) {
            errors += "'order_id' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CommissionCalculateModel{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}

package com.rms.rms.common.view_model;

import java.io.Serializable;

/**
 * homertruong
 */

public class PaymentRejectModel implements Serializable {

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "PaymentRejectModel{" +
                "reason='" + reason + '\'' +
                '}';
    }
}

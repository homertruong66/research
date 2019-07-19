package com.rms.rms.common.view_model;

import java.io.Serializable;

public class NotificationSearchCriteria implements Serializable {

    private static final long serialVersionUID = -4591093826495065277L;

    private String status;
    private String type;

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    @Override
    public String toString () {
        return "NotificationSearchCriteria{" +
                "status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

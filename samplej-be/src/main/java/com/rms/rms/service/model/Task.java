package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/***
 * homertruong
 */

public class Task extends BaseEntityExtensible {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_DONE = "DONE";
    public static final String STATUS_FAILED = "FAILED";

    public static final String ACTION_SEND_EMAILS = "SEND_EMAILS";
    public static final String ACTION_CREATE_NOTIFICATIONS = "CREATE_NOTIFICATIONS";

    private String action;
    private String params;
    private String reasonTaskCreated;
    private String reasonTaskFailed;
    private String status;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getReasonTaskCreated() {
        return reasonTaskCreated;
    }

    public void setReasonTaskCreated(String reasonTaskCreated) {
        this.reasonTaskCreated = reasonTaskCreated;
    }

    public String getReasonTaskFailed() {
        return reasonTaskFailed;
    }

    public void setReasonTaskFailed(String reasonTaskFailed) {
        this.reasonTaskFailed = reasonTaskFailed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "action='" + action + '\'' +
                ", params='" + params + '\'' +
                ", reasonTaskCreated='" + reasonTaskCreated + '\'' +
                ", reasonTaskFailed='" + reasonTaskFailed + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

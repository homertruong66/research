package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

public class TaskCreateModel {

    private String action;
    private String params;
    private String reasonTaskCreated;
    private String reasonTaskFailed;
    private String status;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(action)) {
            errors += "'action' can't be empty! && ";
        }

        if (StringUtils.isBlank(params)) {
            errors += "'params' can't be empty! && ";
        }

        if (StringUtils.isBlank(status)) {
            errors += "'status' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

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
        return "TaskCreateModel{" +
                "action='" + action + '\'' +
                ", params='" + params + '\'' +
                ", reasonTaskCreated='" + reasonTaskCreated + '\'' +
                ", reasonTaskFailed='" + reasonTaskFailed + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

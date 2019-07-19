package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class Notification extends BaseEntityExtensible {

    public static final String READ = "READ";
    public static final String UN_READ = "UN_READ";

    private String message;
    private String status;
    private String targetId;
    private String type;
    private String toUserId;

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getToUserId () {
        return toUserId;
    }

    public void setToUserId (String toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", targetId='" + targetId + '\'' +
                ", type='" + type + '\'' +
                ", toUserId='" + toUserId + '\'' +
                '}';
    }
}

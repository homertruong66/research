package com.rms.rms.common.dto;


import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class NotificationDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -2975774037451341272L;

    private String message;
    private String status;
    private String targetId;
    private String toUserId;
    private String type;
    private String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getToUserId () {
        return toUserId;
    }

    public void setToUserId (String toUserId) {
        this.toUserId = toUserId;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", targetId='" + targetId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

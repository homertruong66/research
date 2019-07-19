package com.rms.rms.common.view_model;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;

public class NotificationCreateModel implements Serializable {

    private static final long serialVersionUID = 6506179742897133035L;

    private String message;
    private String targetId;
    private String type;
    private String toUserId;

    public void escapeHtml() {
        this.message = HtmlUtils.htmlEscape(this.message, "UTF-8");
        this.type = HtmlUtils.htmlEscape(this.type, "UTF-8");
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

    public String validate () {
        String errors = "";

        if ( StringUtils.isBlank(message) ) {
            errors += "'message' can't be null! && ";
        }

        if ( StringUtils.isBlank(targetId) ) {
            errors += "'target_id' can't be null! && ";
        }

        if ( StringUtils.isBlank(type) ) {
            errors += "'type' can't be null! && ";
        }

        if ( StringUtils.isBlank(toUserId) ) {
            errors += "'to_user_id' can't be null! && ";
        }

        return errors.equals("") ? null : errors;
    }

    @Override
    public String toString() {
        return "NotificationCreateModel{" +
                "message='" + message + '\'' +
                ", targetId='" + targetId + '\'' +
                ", type='" + type + '\'' +
                ", toUserId='" + toUserId + '\'' +
                '}';
    }
}

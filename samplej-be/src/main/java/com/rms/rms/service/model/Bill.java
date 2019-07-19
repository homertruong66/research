package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bill extends BaseEntityExtensible {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_CONFIRMED = "CONFIRMED";

    public static final String ACTION_CONFIRM = "CONFIRM";

    public static final Map<String, String> Action2StatusMap = new HashMap<>();
    public static final Map<String, String> StatusFlow = new HashMap<>();

    static {
        Action2StatusMap.put(ACTION_CONFIRM, STATUS_CONFIRMED);

        StatusFlow.put(STATUS_NEW, String.join(" ", STATUS_CONFIRMED));
    }

    private Date deadline;
    private String description;
    private Double fee;
    private String status;
    private Subscriber subscriber;
    private String subscriberId;

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "deadline=" + deadline +
                ", description='" + description + '\'' +
                ", fee=" + fee +
                ", status='" + status + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
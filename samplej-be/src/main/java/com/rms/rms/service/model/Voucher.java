package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Voucher extends BaseEntityExtensible {

    private static final long serialVersionUID = 8457338273441529068L;

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_SENT = "SENT";

    public static final String ACTION_SEND = "SEND";

    public static final Map<String, String> Action2StatusMap = new HashMap<>();
    public static final Map<String, String> StatusFlow = new HashMap<>();

    static {
        Action2StatusMap.put(ACTION_SEND, STATUS_SENT);
        StatusFlow.put(STATUS_NEW, String.join(" ", STATUS_SENT));
    }

    public static final String TYPE_TIME = "TIME";
    public static final String TYPE_VALUE = "VALUE";

    private Double benefit;
    private String code;
    private Date endDate;
    private String name;
    private Date startDate;
    private String status;
    private Subscriber subscriber;
    private String subscriberId;
    private String type;

    public Double getBenefit() {
        return benefit;
    }

    public void setBenefit(Double benefit) {
        this.benefit = benefit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "benefit=" + benefit +
                ", code='" + code + '\'' +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

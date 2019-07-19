package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

public class VoucherDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 5149611128274999903L;

    private Double benefit;
    private String code;
    private Date endDate;
    private String name;
    private Date startDate;
    private String status;
    private SubscriberDto subscriber;
    private String subscriberId;
    private String type;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public SubscriberDto getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubscriberDto subscriber) {
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
        return "VoucherDto{" +
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
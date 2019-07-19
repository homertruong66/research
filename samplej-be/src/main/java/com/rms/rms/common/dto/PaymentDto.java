package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class PaymentDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 8049649373161564187L;

	private AffiliateDto affiliate;
	private String affiliateId;
	private SubscriberDto subscriber;
	private String subscriberId;
	private String reason;
    private String status;
    private Double value;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AffiliateDto getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateDto affiliate) {
        this.affiliate = affiliate;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
	public String toString() {
		return "PaymentDto{" +
                "affiliateId='" + affiliateId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", value=" + value +
                '}';
	}
}

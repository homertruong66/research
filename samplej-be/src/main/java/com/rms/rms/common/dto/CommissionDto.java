package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class CommissionDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4713228468785959182L;

	private AffiliateDto affiliate;
	private String affiliateId;
    private Double earning;      // money
	private DiscountCodeAppliedDto discountCodeApplied;
	private String discountCodeAppliedId;
	private String orderId;
	private String orderNumber;
	private SubscriberDto subscriber;
	private String subscriberId;
	private String type;
    private Double value;        // percentage

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

	public DiscountCodeAppliedDto getDiscountCodeApplied() {
		return discountCodeApplied;
	}

	public void setDiscountCodeApplied(DiscountCodeAppliedDto discountCodeApplied) {
		this.discountCodeApplied = discountCodeApplied;
	}

	public String getDiscountCodeAppliedId() {
        return discountCodeAppliedId;
    }

    public void setDiscountCodeAppliedId(String discountCodeAppliedId) {
        this.discountCodeAppliedId = discountCodeAppliedId;
    }

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }

    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CommissionDto{" +
				", affiliateId='" + affiliateId + '\'' +
                ", discountCodeAppliedId='" + discountCodeAppliedId + '\'' +
                ", earning='" + earning + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
				", subscriberId='" + subscriberId + '\'' +
				", type='" + type + '\'' +
				", value=" + value +
				'}';
	}
}

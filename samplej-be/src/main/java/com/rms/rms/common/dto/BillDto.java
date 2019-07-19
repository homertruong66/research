package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

public class BillDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4713228468785959182L;

	private Date deadline;
	private String description;
	private Double fee;
	private SubscriberDto subscriber;
	private String subscriberId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	@Override
	public String toString() {
		return "BillDto{" +
				"deadline=" + deadline +
				", description='" + description + '\'' +
				", fee=" + fee +
				", subscriberId='" + subscriberId + '\'' +
				'}';
	}
}
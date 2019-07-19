package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.OrderExperienceModel;

import java.io.Serializable;

public class CommissionExperienceDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = -353325418891220139L;

	private Double cdc;
	private Double cbs;
	private Double coanReferrer1;
	private Double coanReferrer2;
	private Double coov;
	private Double copg;
	private Double copq;
	private String message;
	private OrderExperienceModel order;
	private Double orderEarning;
    private Double orderTotal;

    public void formatDoubleProps() {
        cdc = MyNumberUtil.roundDouble(cdc, Integer.valueOf(2));
        cbs = MyNumberUtil.roundDouble(cbs, Integer.valueOf(2));
        coanReferrer1 = MyNumberUtil.roundDouble(coanReferrer1, Integer.valueOf(2));
        coanReferrer2 = MyNumberUtil.roundDouble(coanReferrer2, Integer.valueOf(2));
        coov = MyNumberUtil.roundDouble(coov, Integer.valueOf(2));
        copg = MyNumberUtil.roundDouble(copg, Integer.valueOf(2));
		copq = MyNumberUtil.roundDouble(copq, Integer.valueOf(2));
        orderEarning = MyNumberUtil.roundDouble(orderEarning, Integer.valueOf(2));
        orderTotal = MyNumberUtil.roundDouble(orderTotal, Integer.valueOf(2));
    }

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Double getCdc() {
		return cdc;
	}

	public void setCdc(Double cdc) {
		this.cdc = cdc;
	}

	public Double getCoanReferrer1() {
		return coanReferrer1;
	}

	public void setCoanReferrer1(Double coanReferrer1) {
		this.coanReferrer1 = coanReferrer1;
	}

	public Double getCoanReferrer2() {
		return coanReferrer2;
	}

	public void setCoanReferrer2(Double coanReferrer2) {
		this.coanReferrer2 = coanReferrer2;
	}

	public Double getCbs() {
		return cbs;
	}

	public void setCbs(Double cbs) {
		this.cbs = cbs;
	}

	public Double getCoov() {
		return coov;
	}

	public void setCoov(Double coov) {
		this.coov = coov;
	}

	public Double getCopg() {
		return copg;
	}

	public void setCopg(Double copg) {
		this.copg = copg;
	}

	public Double getCopq() {
		return copq;
	}

	public void setCopq(Double copq) {
		this.copq = copq;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public OrderExperienceModel getOrder() {
		return order;
	}

	public void setOrder(OrderExperienceModel order) {
		this.order = order;
	}

	public Double getOrderEarning() {
		return orderEarning;
	}

	public void setOrderEarning(Double orderEarning) {
		this.orderEarning = orderEarning;
	}

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

	@Override
	public String toString() {
		return "CommissionExperienceDto{" +
				"cdc=" + cdc +
				", cbs=" + cbs +
				", coanReferrer1=" + coanReferrer1 +
				", coanReferrer2=" + coanReferrer2 +
				", coov=" + coov +
				", copg=" + copg +
				", copq=" + copq +
				", message='" + message + '\'' +
				", order=" + order +
				", orderEarning=" + orderEarning +
				", orderTotal=" + orderTotal +
				'}';
	}
}

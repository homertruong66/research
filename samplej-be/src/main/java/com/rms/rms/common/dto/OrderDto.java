package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * homertruong
 */

public class OrderDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 6894726708898956305L;

    private AffiliateDto affiliate;
    private ChannelDto channel;
    private CustomerDto customer;
    private DiscountCodeAppliedDto discountCodeApplied;
    private Double earning;         // total commission earning
    private List<String> infusionTags;
    private Boolean isCustomerConverted;
    private Boolean isGetflySuccess;
    private Boolean isGetResponseSuccess;
    private Boolean isInfusionSuccess;
    private String note;
    private String number;
    private Set<OrderLineDto> orderLines = new HashSet<>();
    private Double total;           // total amount of money
    private String reason;
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AffiliateDto getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateDto affiliate) {
        this.affiliate = affiliate;
    }

    public ChannelDto getChannel() {
        return channel;
    }

    public void setChannel(ChannelDto channel) {
        this.channel = channel;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public DiscountCodeAppliedDto getDiscountCodeApplied() {
        return discountCodeApplied;
    }

    public void setDiscountCodeApplied(DiscountCodeAppliedDto discountCodeApplied) {
        this.discountCodeApplied = discountCodeApplied;
    }

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }

    public List<String> getInfusionTags() {
        return infusionTags;
    }

    public void setInfusionTags(List<String> infusionTags) {
        this.infusionTags = infusionTags;
    }

    public Boolean getIsCustomerConverted() {
        return isCustomerConverted;
    }

    public void setIsCustomerConverted(Boolean isCustomerConverted) {
        this.isCustomerConverted = isCustomerConverted;
    }

    public Boolean getIsGetflySuccess() {
        return isGetflySuccess;
    }

    public void setIsGetflySuccess(Boolean getflySuccess) {
        isGetflySuccess = getflySuccess;
    }

    public Boolean getIsGetResponseSuccess() {
        return isGetResponseSuccess;
    }

    public void setIsGetResponseSuccess(Boolean getResponseSuccess) {
        isGetResponseSuccess = getResponseSuccess;
    }

    public Boolean getIsInfusionSuccess() {
        return isInfusionSuccess;
    }

    public void setIsInfusionSuccess(Boolean infusionSuccess) {
        isInfusionSuccess = infusionSuccess;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    @Override
    public String toString() {
        return "OrderDto{" +
                "affiliate=" + affiliate +
                ", channel=" + channel +
                ", customer=" + customer +
                ", discountCodeApplied=" + discountCodeApplied +
                ", earning=" + earning +
                ", infusionTags=" + infusionTags +
                ", isCustomerConverted=" + isCustomerConverted +
                ", isGetflySuccess=" + isGetflySuccess +
                ", isInfusionSuccess=" + isInfusionSuccess +
                ", note='" + note + '\'' +
                ", number='" + number + '\'' +
                ", orderLines=" + orderLines +
                ", total=" + total +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
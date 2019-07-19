package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * homertruong
 */

public class Order extends BaseEntityExtensible {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_COMMISSIONS_DONE = "COMMISSIONS_DONE";
    public static final String STATUS_REJECTED = "REJECTED";

    public static final String ACTION_APPROVE = "APPROVE";
    public static final String ACTION_CONFIRM_COMMISSIONS_DONE = "CONFIRM_COMMISSIONS_DONE";
    public static final String ACTION_REJECT = "REJECT";

    public static final Map<String, String> Action2StatusMap = new HashMap<>();
    public static final Map<String, String> StatusFlow = new HashMap<>();

    static {
        Action2StatusMap.put(ACTION_APPROVE, STATUS_APPROVED);
        Action2StatusMap.put(ACTION_CONFIRM_COMMISSIONS_DONE, STATUS_COMMISSIONS_DONE);
        Action2StatusMap.put(ACTION_REJECT, STATUS_REJECTED);

        StatusFlow.put(STATUS_NEW, String.join(" ", STATUS_APPROVED, STATUS_REJECTED));
        StatusFlow.put(STATUS_APPROVED, String.join(" ", STATUS_COMMISSIONS_DONE));
        StatusFlow.put(STATUS_REJECTED, String.join(" ", STATUS_APPROVED));
    }

    private Affiliate affiliate;
    private String affiliateId;      // for quick search
    private Channel channel;
    private String channelId;       // for quick search
    private Customer customer;
    private String customerId;      // for quick search
    private DiscountCodeApplied discountCodeApplied;
    private String discountCodeAppliedId;      // for quick search
    private Double earning;         // total commission earning
    private String infusionTags;
    private Boolean isCustomerConverted;
    private Boolean isGetflySuccess;
    private Boolean isGetResponseSuccess;
    private Boolean isInfusionSuccess;
    private String note;
    private String number;
    private Set<OrderLine> orderLines = new HashSet<>();
    private Double total;           // total amount of money
    private String reason;
    private String status;

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public DiscountCodeApplied getDiscountCodeApplied() {
        return discountCodeApplied;
    }

    public void setDiscountCodeApplied(DiscountCodeApplied discountCodeApplied) {
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

    public String getInfusionTags() {
        return infusionTags;
    }

    public void setInfusionTags(String infusionTags) {
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

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
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
        return "Order{" +
                "affiliateId='" + affiliateId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", discountCodeAppliedId='" + discountCodeAppliedId + '\'' +
                ", earning=" + earning +
                ", infusionTags='" + infusionTags + '\'' +
                ", isCustomerConverted=" + isCustomerConverted +
                ", isGetflySuccess=" + isGetflySuccess +
                ", isGetResponseSuccess=" + isGetResponseSuccess +
                ", isInfusionSuccess=" + isInfusionSuccess +
                ", note='" + note + '\'' +
                ", number='" + number + '\'' +
                ", total=" + total +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

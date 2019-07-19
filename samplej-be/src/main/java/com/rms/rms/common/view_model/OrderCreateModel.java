package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * homertruong
 */

public class OrderCreateModel {

    private String affiliateId;
    private String channelDomainName;
    private CustomerCreateModel customer;
    private String discountCode;
    private List<String> infusionTags;
    private String nickname;
    private String note;
    private String number;
    private Set<OrderLineCreateModel> orderLines = new HashSet<>();

    public void escapeHtml() {
        this.note = HtmlUtils.htmlEscape(this.note, "UTF-8");
        this.number = HtmlUtils.htmlEscape(this.number, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(channelDomainName)) {
            errors += "'channel_domain_name' can't be empty! && ";
        }

        if (customer == null) {
            errors += "'customer' can't be null! && ";
        }
        else {
            String customerErrors = customer.validate();
            if (customerErrors != null) {
                errors += customerErrors;
            }
        }

        if (StringUtils.isBlank(number)) {
            errors += "'number' can't be empty! && ";
        }
        else if (StringUtils.containsWhitespace(number)) {
            errors += "'number' can't contain whitespace! && ";
        }

        if (orderLines == null || orderLines.size() == 0) {
            errors += "'order_lines' can't be null or empty! && ";
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (OrderLineCreateModel orderLine: orderLines) {
                String orderLineErrors = orderLine.validate();
                if (orderLineErrors != null) {
                    sb.append(orderLineErrors);
                }
            }
            errors += sb.toString();
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getChannelDomainName() {
        return channelDomainName;
    }

    public void setChannelDomainName(String channelDomainName) {
        this.channelDomainName = channelDomainName;
    }

    public CustomerCreateModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerCreateModel customer) {
        this.customer = customer;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public List<String> getInfusionTags() {
        return infusionTags;
    }

    public void setInfusionTags(List<String> infusionTags) {
        this.infusionTags = infusionTags;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Set<OrderLineCreateModel> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLineCreateModel> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "OrderCreateModel{" +
                "affiliateId='" + affiliateId + '\'' +
                ", channelDomainName='" + channelDomainName + '\'' +
                ", customer=" + customer +
                ", discountCode='" + discountCode + '\'' +
                ", nickname='" + nickname + '\'' +
                ", note='" + note + '\'' +
                ", number='" + number + '\'' +
                ", orderLines=" + orderLines +
                '}';
    }
}

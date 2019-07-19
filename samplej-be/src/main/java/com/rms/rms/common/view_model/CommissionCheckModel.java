package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class CommissionCheckModel {

    private String affiliateId;
    private String customerEmail;
    private Double discount;
    private Set<OrderLineCheckModel> orderLines = new HashSet<>();

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(affiliateId)) {
            errors += "'affiliate_id' can't be null or empty! && ";
        }

        if (!StringUtils.isBlank(customerEmail) && !MyStringUtil.isEmailFormatCorrect(customerEmail)) {
            errors += "'customer_email' has incorrect format! && ";
        }

        if (discount != null) {
            if (Double.compare(discount, 0) < 0 || Double.compare(discount, 1) > 0) {
                errors += "'discount' must >= 0 and <= 1 && ";
            }
        }

        if (orderLines == null || orderLines.size() == 0) {
            errors += "'order_lines' can't be null or empty! && ";
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (OrderLineCheckModel orderLine: orderLines) {
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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<OrderLineCheckModel> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLineCheckModel> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "CommissionCheckModel{" +
                "affiliateId='" + affiliateId + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", discount=" + discount +
                ", orderLines=" + orderLines +
                '}';
    }
}

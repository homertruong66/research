package com.rms.rms.common.view_model;

import java.util.HashSet;
import java.util.Set;

public class OrderExperienceModel {

    private Set<OrderLineExperienceModel> orderLines = new HashSet<>();

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (orderLines == null || orderLines.size() == 0) {
            errors += "'order_lines' can't be null or empty! && ";
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (OrderLineExperienceModel orderLine: orderLines) {
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
    public Set<OrderLineExperienceModel> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLineExperienceModel> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "OrderExperienceModel{" +
                "orderLines=" + orderLines +
                '}';
    }
}

package com.rms.rms.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

public class OrderViewDto implements Serializable {

    private static final long serialVersionUID = 454773087719954342L;

    private OrderDto order;
    private List<CommissionDto> commissions = new ArrayList<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public List<CommissionDto> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<CommissionDto> commissions) {
        this.commissions = commissions;
    }
}
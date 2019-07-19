package com.tech3s.jcoder.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseEntity {
	
    private String code;
    private List<OrderLine> orderLineList = new ArrayList<>();
    private Customer customer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<OrderLine> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<OrderLine> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
	public String toString() {
		return "Order [code=" + code + ", orderLineList=" + orderLineList + ", customer=" + customer + "]";
	}	
}

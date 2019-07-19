package com.rms.rms.common.view_model;

public class OrderLineCheckModel {

    private Double commission;
    private String productId;
    private Double price;
    private Integer quantity;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (commission != null) {
            if (Double.compare(commission, 0) < 0 || Double.compare(commission, 1) > 0) {
                errors += "'commission' must >= 0 and <= 1! && ";
            }
        }

        if (productId == null) {
            errors += "'product_id' can't be null! && ";
        }

        if (price == null) {
            errors += "'price' can't be null! && ";
        }

        if (quantity == null) {
            errors += "'quantity' can't be null! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //
    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLineCheckModel{" +
                "commission=" + commission +
                ", productId='" + productId + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

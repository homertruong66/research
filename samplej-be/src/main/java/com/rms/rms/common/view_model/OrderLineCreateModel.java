package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class OrderLineCreateModel {

    private Double commission;
    private ProductCreateModel product;
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

        if (product == null) {
            errors += "'product' can't be null! && ";
        }
        // validate product in OrderService, do not validate here

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

    public ProductCreateModel getProduct() {
        return product;
    }

    public void setProduct(ProductCreateModel product) {
        this.product = product;
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
        return "OrderLineCreateModel{" +
                "commission=" + commission +
                "product=" + product +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

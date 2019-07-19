package com.rms.rms.common.view_model;

public class OrderLineExperienceModel {

    private Double commission;
    private ProductExperienceModel product;
    private Double price;
    private Integer quantity;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (product == null) {
            errors += "'product' can't be null! && ";
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

    public ProductExperienceModel getProduct() {
        return product;
    }

    public void setProduct(ProductExperienceModel product) {
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
        return "OrderLineExperienceModel{" +
                "commission=" + commission +
                ", product=" + product +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

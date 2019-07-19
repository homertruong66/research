package com.rms.rms.common.view_model;

import java.util.List;

public class ProductSetProductsAssignModel {

    private List<String> productIds;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public String validate () {
        String errors = "";

        if (productIds == null || productIds.size() == 0) {
            errors += "'product_ids' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    @Override
    public String toString() {
        return "ProductSetProductsAssignModel{" +
                "productIds=" + productIds +
                '}';
    }
}
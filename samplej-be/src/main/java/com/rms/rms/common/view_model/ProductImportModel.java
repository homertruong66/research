package com.rms.rms.common.view_model;

import org.springframework.util.CollectionUtils;

import java.util.Set;

public class ProductImportModel {

    private Set<ProductCreateModel> products;

    public void escapeHtml() {
        for (ProductCreateModel product : products) {
            product.escapeHtml();
        }
    }

    public String validate() {
        String errors = "";

        if (CollectionUtils.isEmpty(products)) {
            errors += "'products' can't be empty! && ";
        }

        if (!CollectionUtils.isEmpty(products)) {
            for (ProductCreateModel product : products) {
                if (product.validate() != null) {
                    errors += product.validate();
                }
            }
        }

        return errors.equals("") ? null : errors;
    }

    public Set<ProductCreateModel> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductCreateModel> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductImportModel{" +
                "products=" + products +
                '}';
    }
}

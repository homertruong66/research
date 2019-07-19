package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class ProductSetProduct extends BaseEntityExtensible {

    private Product product;
    private String productId;
    private ProductSet productSet;
    private String productSetId;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductSet getProductSet() {
        return productSet;
    }

    public void setProductSet(ProductSet productSet) {
        this.productSet = productSet;
    }

    public String getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(String productSetId) {
        this.productSetId = productSetId;
    }

    @Override
    public String toString() {
        return "ProductSetProduct{" +
                "product=" + product +
                ", productId='" + productId + '\'' +
                ", productSet=" + productSet +
                ", productSetId='" + productSetId + '\'' +
                '}';
    }
}

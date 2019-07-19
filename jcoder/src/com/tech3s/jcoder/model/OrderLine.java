package com.tech3s.jcoder.model;

public class OrderLine extends BaseEntity {

	private double price;
    private int quantity;
    private Product product;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
	public String toString() {
		return "OrderLine [price=" + price + ", quantity=" + quantity + ", product=" + product + "]";
	}
}

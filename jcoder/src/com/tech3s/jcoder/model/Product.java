package com.tech3s.jcoder.model;

public class Product extends BaseEntity {

    private String title;
    private String description;
    private double price;
    private int quantity;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    @Override
	public String toString() {
		return "Product [title=" + title + ", description=" + description + ", price=" + price + ", quantity=" + quantity + "]";
	}
}

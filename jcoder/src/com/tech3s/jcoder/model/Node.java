package com.tech3s.jcoder.model;

public class Node extends BaseEntity {

    private Customer customer;
    private Node left;
    private Node right;
    
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "Node [customer=" + customer + ", left=" + left + ", right=" + right + "]";
	}
}

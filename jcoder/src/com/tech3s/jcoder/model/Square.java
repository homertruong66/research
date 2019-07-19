package com.tech3s.jcoder.model;

public class Square extends Shape {

    public void draw() {
        System.out.println("Draw the Square with edge=" + this.getEdge() + " and color=" + getColor());
    }

    public void erase() {
        System.out.println("Erasing the Square...");
        System.out.println("Erased the Square with edge=" + getEdge() + " done!");
    }

    @Override
    public void area() {
        System.out.println("Area of Square: " + this.getEdge() * this.getEdge());
    }
}

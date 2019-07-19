package com.tech3s.jcoder.model;

public class Circle extends Shape {

    @Override
    public void draw() {
        System.out.println("Draw the Circle with radius=" + this.getRadius() + " and color=" + getColor());
    }

    // Overload
    public void draw(String customColor) {
        System.out.println("Draw the Circle with radius=" + this.getRadius() + " and custom color=" + customColor);
    }

    @Override
    public void erase() {
        System.out.println("Erasing the Circle...");
        System.out.println("Erased the Circle with radius=" + getRadius() + " done!");
    }

    @Override
    public void area() {
        System.out.println("Area of Circle: " + Math.PI * this.getRadius() * this.getRadius());
    }
}

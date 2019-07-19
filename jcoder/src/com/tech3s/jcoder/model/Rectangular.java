package com.tech3s.jcoder.model;

public class Rectangular extends Shape {

    public void draw() {
        System.out.println("Draw the Rectangular with width=" + this.getWidth() + " and height=" + getHeight()  + " and color=" + getColor());
    }

    public void erase() {
        System.out.println("Erasing the Rectangular...");
        System.out.println("Erased the Rectangular with width=" + this.getWidth() + " and height=" + getHeight() + " done!");
    }

    @Override
    public void area() {
        System.out.println("Area of Rectangular: " + this.getWidth() * this.getHeight());
    }
}

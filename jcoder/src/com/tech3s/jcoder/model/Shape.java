package com.tech3s.jcoder.model;

public abstract class Shape {

    public static int MAX_WIDTH = 100;

    private int width;
    private int height;
    private int edge;
    private int radius;
    private String color;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getEdge() {
        return edge;
    }

    public void setEdge(int edge) {
        this.edge = edge;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void draw() {
        System.out.println(width + " - " + height + " - " + radius);
    }

    public void erase() {
    }

    public abstract void area();

    @Override
    public String toString() {
        return "Shape{type=" + this.getClass().getSimpleName() +
                ", width=" + width +
                ", height=" + height +
                ", edge=" + edge +
                ", radius=" + radius +
                ", color='" + color + '\'' +
                '}';
    }
}

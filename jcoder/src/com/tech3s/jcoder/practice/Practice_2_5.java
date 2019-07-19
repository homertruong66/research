package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.model.Circle;
import com.tech3s.jcoder.model.Rectangular;
import com.tech3s.jcoder.model.Shape;
import com.tech3s.jcoder.model.Square;

public class Practice_2_5 {

	public static void main(String[] args) {
        System.out.println("Play with some types of Shape...\n");

		Rectangular rectangular = new Rectangular();
		rectangular.setWidth(66);
		rectangular.setHeight(12);
		rectangular.setColor("RED");
		playWithShape(rectangular);

		Square square = new Square();
		square.setEdge(96);
		square.setColor("GREEN");
		playWithShape(square);

		Circle circle = new Circle();
		circle.setRadius(110);
		if (circle.getRadius() > Shape.MAX_WIDTH) {
			circle.setRadius(Shape.MAX_WIDTH);
		}
		circle.setColor("BLUE");
		playWithShape(circle);
	}

	private static void playWithShape(Shape shape) {
		System.out.println(shape);	// inheritance
		shape.draw();				// polymorphism
		if (shape instanceof Circle) {
			Circle circle = (Circle) shape;
			circle.draw("RED");
		}
		shape.area();				// polymorphism
		shape.erase();				// polymorphism
		System.out.println();
	}

}

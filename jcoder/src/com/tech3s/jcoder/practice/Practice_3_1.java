package com.tech3s.jcoder.practice;

import com.tech3s.jcoder.lib.MathLibrary;

import java.util.List;
import java.util.Scanner;

public class Practice_3_1 {

	public static void main(String[] args) {	
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Solve equation: a*x + b = 0");
		System.out.print("a: ");
		double a = scanner.nextDouble();
		System.out.print("b: ");
		double b = scanner.nextDouble();
		Double r1  = MathLibrary.solveFirstEquation(a, b);
		if (r1 == null) {
			System.out.println("No root!");
		}
		else {
			System.out.println("Root: " + r1);
		}
		
		System.out.println("Solve equation: a*x*x + b*x +c = 0");
		System.out.print("a: ");
		a = scanner.nextDouble();
		System.out.print("b: ");
		b = scanner.nextDouble();
		System.out.print("c: ");
		double c = scanner.nextDouble();
		List<Double> roots = MathLibrary.solveSecondEquation(a, b, c);
		if (roots.size() == 0) {
			System.out.println("No root!");
		}
		else {
			System.out.println("Root1: " + roots.get(0) + " - Root2: " + roots.get(1));
		}
		
		scanner.close();
	}

}
